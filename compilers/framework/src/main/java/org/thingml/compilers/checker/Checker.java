/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers.checker;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;
import org.thingml.compilers.Context;
import org.thingml.compilers.checker.genericRules.AutotransitionCycles;
import org.thingml.compilers.checker.genericRules.ConnectorCycles;
import org.thingml.compilers.checker.genericRules.ControlStructures;
import org.thingml.compilers.checker.genericRules.DuplicatedMessageInPort;
import org.thingml.compilers.checker.genericRules.FunctionUsage;
import org.thingml.compilers.checker.genericRules.InternalTransitions;
import org.thingml.compilers.checker.genericRules.LostMessages;
import org.thingml.compilers.checker.genericRules.MessagesUsage;
import org.thingml.compilers.checker.genericRules.NonDeterministicTransitions;
import org.thingml.compilers.checker.genericRules.PortsUsage;
import org.thingml.compilers.checker.genericRules.PropertyInitialization;
import org.thingml.compilers.checker.genericRules.StatesUsage;
import org.thingml.compilers.checker.genericRules.ThingsUsage;
import org.thingml.compilers.checker.genericRules.VariableUsage;
import org.thingml.xtext.helpers.AnnotatedElementHelper;
import org.thingml.xtext.helpers.ThingMLElementHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.ThingMLModel;

/**
 *
 * @author sintef
 */
abstract public class Checker {
    public Set<CheckerInfo> Errors;
    public Set<CheckerInfo> Warnings;
    public Set<CheckerInfo> Notices;
    public List<ErrorWrapper> wrappers;
    public TypeChecker typeChecker = new TypeChecker();
    private Set<Rule> Rules;
    private String compiler;
    private String generic;
    public Context ctx;

    public Checker(String compiler) {
        Rules = new HashSet<Rule>();
        Errors = new HashSet<CheckerInfo>();
        Warnings = new HashSet<CheckerInfo>();
        Notices = new HashSet<CheckerInfo>();
        wrappers = new ArrayList<ErrorWrapper>();
        wrappers.add(new EMFWrapper());

        this.ctx = new Context(null);
        this.compiler = compiler;
        generic = "ThingML";

        Rules.add(new ThingsUsage());
        Rules.add(new PortsUsage());
        Rules.add(new MessagesUsage());
        Rules.add(new ConnectorCycles());
        Rules.add(new InternalTransitions());
        Rules.add(new AutotransitionCycles());
        Rules.add(new NonDeterministicTransitions());
        Rules.add(new FunctionUsage());
        Rules.add(new StatesUsage());
        Rules.add(new VariableUsage());
        Rules.add(new ControlStructures());
        Rules.add(new DuplicatedMessageInPort());
        Rules.add(new PropertyInitialization());
        Rules.add(new LostMessages());
    }

    public void do_generic_check(Configuration cfg) {
        List<String> notChecked = AnnotatedElementHelper.annotation(cfg, "SuppressWarnings");
        long start = System.currentTimeMillis();
        for (Rule r : Rules) {
            if (!notChecked.contains(r.getName()))
                r.check(cfg, this);
        }
        System.out.println("checker took " + (System.currentTimeMillis() - start) + " ms");
    }

    public void do_generic_check(ThingMLModel model) {
        long start = System.currentTimeMillis();
        for (Rule r : Rules) {
            r.check(model, this);
        }
        System.out.println("checker took " + (System.currentTimeMillis() - start) + " ms");
    }

    // Must be implemented and must contain a call to do_generic_check(cfg)
    abstract public void do_check(Configuration cfg);


    // ---------------------- Accessors ----------------------

    public void addError(String msg, EObject el) {
        Errors.add(new CheckerInfo(InfoType.ERROR, compiler, msg, el));
        for (ErrorWrapper wrapper : wrappers) {
            wrapper.addError(msg, el);
        }
    }

    public void addError(String compiler, String msg, EObject el) {
        Errors.add(new CheckerInfo(InfoType.ERROR, compiler, msg, el));
        for (ErrorWrapper wrapper : wrappers) {
            wrapper.addError(msg, el);
        }
    }

    public void addGenericError(String msg, EObject el) {
        Errors.add(new CheckerInfo(InfoType.ERROR, generic, msg, el));
        for (ErrorWrapper wrapper : wrappers) {
            wrapper.addError(msg, el);
        }
    }

    public void addWarning(String msg, EObject el) {
        Warnings.add(new CheckerInfo(InfoType.WARNING, compiler, msg, el));
        for (ErrorWrapper wrapper : wrappers) {
            wrapper.addWarning(msg, el);
        }
    }

    public void addWarning(String compiler, String msg, EObject el) {
        Warnings.add(new CheckerInfo(InfoType.WARNING, compiler, msg, el));
        for (ErrorWrapper wrapper : wrappers) {
            wrapper.addWarning(msg, el);
        }
    }

    public void addGenericWarning(String msg, EObject el) {
        Warnings.add(new CheckerInfo(InfoType.WARNING, generic, msg, el));
        for (ErrorWrapper wrapper : wrappers) {
            wrapper.addWarning(msg, el);
        }
    }

    public void addNotice(String msg, EObject el) {
        Notices.add(new CheckerInfo(InfoType.NOTICE, compiler, msg, el));
    }

    public void addNotice(String compiler, String msg, EObject el) {
        Notices.add(new CheckerInfo(InfoType.NOTICE, compiler, msg, el));
    }

    public void addGenericNotice(String msg, EObject el) {
        Notices.add(new CheckerInfo(InfoType.NOTICE, generic, msg, el));
    }

    public boolean containsErrors() {
        return Errors.isEmpty();
    }

    public boolean containsWarnings() {
        return Warnings.isEmpty();
    }

    public boolean containsNotices() {
        return Notices.isEmpty();
    }

    public void printReport() {
       new Thread() {
            @Override
            public void run() {
                super.run();
                AnsiConsole.systemInstall();
                ctx.ansi.fg(Ansi.Color.BLUE);
                printNotices();
                ctx.ansi.fg(Ansi.Color.MAGENTA);
                printWarnings();
                ctx.ansi.fg(Ansi.Color.RED);
                printErrors();
                ctx.ansi.reset();
                if (ctx.ansi.isEnabled()) System.out.print(ctx.ansi);
                AnsiConsole.systemUninstall();
            }
        }.start();
    }

    public void printErrors() {
        if(ctx.ansi.isEnabled()) {
            for (final CheckerInfo i : Errors) {
                ctx.ansi.a(i.toString());
            }
        } else {
            for (final CheckerInfo i : Errors) {
                System.out.print(i.toString());
            }
        }
    }

    public void printWarnings() {
        if(ctx.ansi.isEnabled()) {
            for (final CheckerInfo i : Warnings) {
                ctx.ansi.a(i.toString());
            }
        } else {
            for (final CheckerInfo i : Warnings) {
                System.out.print(i.toString());
            }
        }
    }

    public void printNotices() {
        if(ctx.ansi.isEnabled()) {
            for (CheckerInfo i : Notices) {
                ctx.ansi.a(i.toString());
            }
        } else {
            for (CheckerInfo i : Notices) {
                System.out.print(i.toString());
            }
        }
    }

    // ---------------------- Structures ----------------------

    public enum InfoType {ERROR, WARNING, NOTICE}

    ;

    public class CheckerInfo {
        public InfoType type;
        public String source;
        public String message;
        public EObject element;

        public CheckerInfo(InfoType type, String source, String message, EObject element) {
            this.type = type;
            this.source = source;
            this.message = message;
            this.element = element;
        }

        public String print(EObject el) {
        	return ThingMLElementHelper.getName(el);
        }

        public String toString() {
            String t;
            switch (type) {
                case NOTICE:
                    t = "NOTICE";
                    break;

                case WARNING:
                    t = "WARNING";
                    break;

                case ERROR:
                    t = "ERROR";
                    break;
                default:
                    t = "";
            }


            return "[" + t + "] " + source + ": " + message + " (in " + print(element) + ")\n";
        }
    }
}
