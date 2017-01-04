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
package org.thingml.compilers.registry;

import org.thingml.testconfigurationgenerator.TestConfigurationGenerator;
import org.thingml.thingmltools.ThingMLTool;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author sintef
 */
public class ThingMLToolRegistry {

    private static ThingMLToolRegistry instance;
    private HashMap<String, ThingMLTool> tools = new HashMap<String, ThingMLTool>();

    public static ThingMLToolRegistry getInstance() {

        if (instance == null) {
            instance = new ThingMLToolRegistry();
            instance.addTool(new TestConfigurationGenerator());

        }

        return instance;
    }

    public Set<String> getToolIds() {
        return tools.keySet();
    }

    public Collection<ThingMLTool> getToolPrototypes() {
        return tools.values();
    }

    public void addTool(ThingMLTool t) {
        tools.put(t.getID(), t);
    }

    public ThingMLTool createToolInstanceByName(String id) {
        if (tools.get(id) == null) {
            return null;
        } else {
            return tools.get(id).clone();
        }
    }
}
