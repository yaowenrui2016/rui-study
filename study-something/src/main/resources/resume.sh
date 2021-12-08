#!/usr/bin/env bash

url=http://localhost:8040/api/sys-notify/sysNotifySourceMApps/migrateSourceAppModule
xServiceName=73456775666d4c416f73776139584a4131432f6847413d3d

curl -i -H x-service-name:$xServiceName -X POST $url