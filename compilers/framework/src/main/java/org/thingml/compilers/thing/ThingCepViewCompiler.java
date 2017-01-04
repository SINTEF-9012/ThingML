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
package org.thingml.compilers.thing;

import org.sintef.thingml.Filter;
import org.sintef.thingml.LengthWindow;
import org.sintef.thingml.TimeWindow;
import org.sintef.thingml.ViewSource;
import org.thingml.compilers.Context;

/**
 * @author ludovic
 */
public class ThingCepViewCompiler {
    public void generate(ViewSource view, StringBuilder builder, Context context) {
        if (view instanceof Filter) {
            generate((Filter) view, builder, context);
        } else if (view instanceof TimeWindow) {
            generate((TimeWindow) view, builder, context);
        } else if (view instanceof LengthWindow) {
            generate((LengthWindow) view, builder, context);
        } else {
            throw new UnsupportedOperationException("The view source (" + view.getClass().getName() + ")" +
                    "is unknown in the Java compiler. Please update it as the ThingML model has been changed.");
        }
    }

    public void generate(Filter filter, StringBuilder builder, Context context) {
        throw new UnsupportedOperationException("Cep views are platform-specific.");
    }

    public void generate(TimeWindow timeWindow, StringBuilder builder, Context context) {
        throw new UnsupportedOperationException("Cep views are platform-specific.");
    }

    public void generate(LengthWindow lengthWindow, StringBuilder builder, Context context) {
        throw new UnsupportedOperationException("Cep views are platform-specific.");
    }
}
