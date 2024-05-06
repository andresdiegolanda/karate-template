package examples.ld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper; // Needed for JsonNode to LDValue conversion
import com.launchdarkly.sdk.LDUser;
import com.launchdarkly.sdk.LDValue; // Import LDValue
import com.launchdarkly.sdk.server.LDClient;
import com.launchdarkly.sdk.server.LDConfig;

public class LaunchDarklyClient {
	private static final String SDK_KEY = System.getenv("LD_SDK_KEY");
	private static LDClient client = null;
	private static ObjectMapper objectMapper = new ObjectMapper(); // For converting JsonNode to LDValue

	public static synchronized LDClient getClient() {
		if (client == null) {
			LDConfig config = new LDConfig.Builder().build();
			client = new LDClient(SDK_KEY, config);
		}
		return client;
	}

	public static boolean getFeatureFlagBoolean(String featureKey, String username) {
		LDUser user = new LDUser.Builder(username).build();
		return getClient().boolVariation(featureKey, user, false);
	}

	public static String getFeatureFlagString(String featureKey, String username) {
		LDUser user = new LDUser.Builder(username).build();
		return getClient().stringVariation(featureKey, user, "default");
	}

	public static int getFeatureFlagInt(String featureKey, String username) {
		LDUser user = new LDUser.Builder(username).build();
		return getClient().intVariation(featureKey, user, 0);
	}

	public static double getFeatureFlagDouble(String featureKey, String username) {
		LDUser user = new LDUser.Builder(username).build();
		return getClient().doubleVariation(featureKey, user, 0.0);
	}

	public static JsonNode getFeatureFlagJson(String featureKey, String username) throws JsonProcessingException {
		LDUser user = new LDUser.Builder(username).build();
		// Default JsonNode
		JsonNode defaultNode = objectMapper.createObjectNode();
		// Converting JsonNode to LDValue
		LDValue defaultValue = LDValue.parse(objectMapper.writeValueAsString(defaultNode));
		LDValue result = getClient().jsonValueVariation(featureKey, user, defaultValue);
		return objectMapper.readTree(result.toJsonString());
	}
}
