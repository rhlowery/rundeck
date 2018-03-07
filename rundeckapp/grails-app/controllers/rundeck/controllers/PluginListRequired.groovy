/*
 * Copyright 2017 Rundeck, Inc. (http://rundeck.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rundeck.controllers

import rundeck.services.PluginService

/**
 * Use with {@link AfterInterceptorChain}
 * @author greg
 * @since 5/19/17
 */
trait PluginListRequired {

    abstract Map<String, Class> getRequiredPluginTypes()
    abstract PluginService getPluginService()

    Collection<String> getRequiredPluginActionNames() {
        null
    }

    Collection<String> getRequiredPluginExcludedActionNames() {
        null
    }


    @AfterInterceptor
    def loadRequiredPlugins(Map model) {
        if ((requiredPluginActionNames != null && !(actionName in requiredPluginActionNames)) || (requiredPluginExcludedActionNames != null && actionName in requiredPluginExcludedActionNames)) {
            return
        }
        for (Map.Entry<String, Class> entry : getRequiredPluginTypes()?.entrySet()) {
            model[entry.key] = pluginService.listPlugins(entry.value)
        }
    }
}
