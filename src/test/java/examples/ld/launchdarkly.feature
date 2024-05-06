Feature: Validate feature flags with LaunchDarkly

  Background:
    * def LaunchDarklyClient = Java.type('examples.ld.LaunchDarklyClient')
    * def username = 'andresdiegolanda@gmail.com'
    * def stringFeatureKey = 'stringFlag'
    * def booleanFeatureKey = 'test-feature'

  Scenario: Check if the feature flag is enabled for the user
    * def stringFeature = LaunchDarklyClient.getFeatureFlagString(stringFeatureKey, username)
    * print '@@@stringfeature value: ', stringFeature
    * def booleanFeature = LaunchDarklyClient.getFeatureFlagBoolean(booleanFeatureKey, username)
    * print '@@@booleanfeature value: ', booleanFeature