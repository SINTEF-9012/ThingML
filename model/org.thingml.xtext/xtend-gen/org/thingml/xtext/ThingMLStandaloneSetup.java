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
/**
 * generated by Xtext 2.10.0
 */
package org.thingml.xtext;

import org.thingml.xtext.ThingMLStandaloneSetupGenerated;

/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
@SuppressWarnings("all")
public class ThingMLStandaloneSetup extends ThingMLStandaloneSetupGenerated {
  public static void doSetup() {
    ThingMLStandaloneSetup _thingMLStandaloneSetup = new ThingMLStandaloneSetup();
    _thingMLStandaloneSetup.createInjectorAndDoEMFRegistration();
  }
}
