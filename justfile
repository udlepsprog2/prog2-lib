# Publish snapshots to maven repository
publish-snapshot:
    @echo "Publishing snapshot to maven repository..."
    ./gradlew publishAggregationToCentralSnapshots

# Publish releases to maven repository
publish-release:
    @echo "Publishing release to maven repository..."
    ./gradlew publishAggregationToCentralPortal

# Publish to maven local
publish-local:
    @echo "Publishing to maven local..."
    ./gradlew nmcpPublishAggregationToMavenLocal

# Create ZIP
create-zip:
    @echo "Creating ZIP file..."
    ./gradlew nmcpZipAggregation

