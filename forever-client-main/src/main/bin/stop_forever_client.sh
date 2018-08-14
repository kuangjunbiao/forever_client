echo "Killing: `cat run_forever_client_main.pid`"
kill -9 `run_forever_client_main.pid`
rm -rf run_forever_client_main.pid
