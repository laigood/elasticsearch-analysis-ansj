/*
 * Licensed to Elastic Search and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Elastic Search licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import static org.elasticsearch.client.Requests.putMappingRequest;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class AnsjAnalyzerEsIntegrateTest {

    public static void main(String[] args) throws Exception {
        Node node = NodeBuilder.nodeBuilder().settings(ImmutableSettings.settingsBuilder().put("gateway.type", "local").put("cluster.name", "cshop")).node();
        Thread.sleep(1000);  
        Client client = node.client();
        client.admin().indices().prepareCreate("test").execute().actionGet();
        client.admin().indices().prepareRefresh().execute().actionGet();
        
        XContentBuilder content = jsonBuilder().startObject()
        .startObject("test")
          .startObject("properties")       
            .startObject("test")
              .field("type", "string")
              .field("term_vector", "with_positions_offsets")
              .field("indexAnalyzer", "ansj")
              .field("searchAnalyzer", "ansj")
            .endObject() 
            .endObject()
         .endObject()
       .endObject();
        
        client.admin().indices().putMapping(putMappingRequest("test").type("test").source(content)).actionGet();           
        Thread.sleep(100000000);
    }
}
