/**
 *
 * Copyright 2016 Xiaofei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package eventbus.hermes.sender;

import java.lang.reflect.Method;

import eventbus.hermes.HermesService;
import eventbus.hermes.wrapper.MethodWrapper;
import eventbus.hermes.wrapper.ObjectWrapper;
import eventbus.hermes.wrapper.ParameterWrapper;

/**
 * Created by Xiaofei on 16/4/8.
 */
public class UtilityGettingSender extends Sender {

    public UtilityGettingSender(Class<? extends HermesService> service, ObjectWrapper object) {
        super(service, object);
    }

    @Override
    protected MethodWrapper getMethodWrapper(Method method, ParameterWrapper[] parameterWrappers) {
        return null;
    }
}