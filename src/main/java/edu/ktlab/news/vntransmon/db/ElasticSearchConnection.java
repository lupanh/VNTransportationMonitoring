package edu.ktlab.news.vntransmon.db;

import java.util.List;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class ElasticSearchConnection {
	private Client client;
	private Node node;

	public ElasticSearchConnection() {
		node = new NodeBuilder().node();
		client = node.client();
	}

	@SuppressWarnings("resource")
	public ElasticSearchConnection(String ipAddress) {
		client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(
				ipAddress, 9300));
	}

	public void closeConnection() {
		client.close();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void createIndexResponse(String indexname, String type, List<String> jsondata) {
		IndexRequestBuilder requestBuilder = client.prepareIndex(indexname, type).setRefresh(true);

		for (int i = 0; i < jsondata.size(); i++) {
			requestBuilder.setSource(jsondata.get(i)).execute().actionGet();
		}
	}

	public IndexResponse createIndexResponse(String indexname, String type, String jsondata) {
		IndexResponse response = client.prepareIndex(indexname, type).setSource(jsondata).execute()
				.actionGet();
		return response;
	}

	public DeleteByQueryResponse deleteAll(String indexname, String type) {
		DeleteByQueryResponse response = client.prepareDeleteByQuery(indexname)
				.setQuery(QueryBuilders.termQuery("_type", type)).execute().actionGet();
		return response;
	}

	public void deleteMapping(String indexname, String type) {
		client.admin().indices().prepareDeleteMapping(indexname).setType(type).execute();
	}

	public void deleteIndex(String indexname) {
		DeleteIndexResponse delete = client.admin().indices()
				.delete(new DeleteIndexRequest(indexname)).actionGet();
		if (!delete.isAcknowledged()) {
			System.out.println("Index wasn't deleted");
		}
	}

	public boolean checkExistIdPost(String indexname, String id) {
		try {
			SearchResponse response = client.prepareSearch(indexname)
					.setQuery(QueryBuilders.termQuery("id", id)).execute().actionGet();
			if (response.getHits().getTotalHits() > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
}
