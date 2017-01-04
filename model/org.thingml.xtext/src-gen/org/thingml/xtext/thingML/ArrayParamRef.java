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
package org.thingml.xtext.thingML;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Array Param Ref</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.thingml.xtext.thingML.ArrayParamRef#getParameterRef <em>Parameter Ref</em>}</li>
 * </ul>
 *
 * @see org.thingml.xtext.thingML.ThingMLPackage#getArrayParamRef()
 * @model
 * @generated
 */
public interface ArrayParamRef extends ElmtProperty
{
  /**
   * Returns the value of the '<em><b>Parameter Ref</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Parameter Ref</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Parameter Ref</em>' reference.
   * @see #setParameterRef(Parameter)
   * @see org.thingml.xtext.thingML.ThingMLPackage#getArrayParamRef_ParameterRef()
   * @model
   * @generated
   */
  Parameter getParameterRef();

  /**
   * Sets the value of the '{@link org.thingml.xtext.thingML.ArrayParamRef#getParameterRef <em>Parameter Ref</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Parameter Ref</em>' reference.
   * @see #getParameterRef()
   * @generated
   */
  void setParameterRef(Parameter value);

} // ArrayParamRef
