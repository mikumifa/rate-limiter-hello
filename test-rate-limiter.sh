#!/bin/bash

total_requests=10000
base_url="http://localhost:31015/hello"  # 替换成你的应用程序的实际 URL

for ((i = 1; i <= total_requests; i++)); do
  echo "Sending request #$i"
  response=$(curl -s "$base_url")
  echo "Response: $response"
done

wait
