#!/bin/sh

echo "Waiting for config server..."

until curl --fail http://config-server:8888/actuator/health; do
  >&2 echo "Config Server not ready, waiting..."
  sleep 5
done

echo "Config Server ready, star app"
exec java -jar sensor-data-producer.jar