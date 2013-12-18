/*
 Copyright 2013 SimplifyOps Inc, <http://simplifyops.com>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.dtolabs.rundeck.core.resources;

import com.dtolabs.rundeck.core.common.INodeSet;

/**
 * Abstract caching model source. calls to getNodes will attempt to use the delegate to get nodes.  If successful the
 * nodes will be stored in the cache with a call to {@link #storeNodesInCache(com.dtolabs.rundeck.core.common.INodeSet)}.
 * If any exception is thrown it will be caught.  finally getNodes returns the result of {@link #loadCachedNodes()}
 */
public abstract class CachingResourceModelSource extends ExceptionCatchingResourceModelSource {
    public CachingResourceModelSource(ResourceModelSource delegate) {
        super(delegate);
    }

    @Override
    INodeSet returnResultNodes(INodeSet nodes) throws ResourceModelSourceException {
        if (null != nodes) {
            storeNodesInCache(nodes);
            return nodes;
        }
        return loadCachedNodes();
    }

    /**
     * Store the nodes in a cache
     *
     * @param nodes
     */
    abstract void storeNodesInCache(INodeSet nodes) throws ResourceModelSourceException;

    /**
     * Load nodes from the cache
     *
     * @return
     */
    abstract INodeSet loadCachedNodes() throws ResourceModelSourceException;

}
