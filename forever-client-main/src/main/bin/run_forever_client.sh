JAVA_OPTS="-Xms512m -Xmx1024m"

echo "forever-client-main on...."

if find -name run_forever_client_main.pid | grep "run_forever_client_main.pid";
then
echo "forever-client-main is running..."
  exit
fi 

CLASSPATH="$CLASSPATH":"./forever-client-main-1.0-SNAPSHOT.jar"
echo $CLASSPATH
LIBPATH="./lib"
if [ -d "$LIBPATH" ]; then
  for i in "$LIBPATH"/*.jar; do
    CLASSPATH="$CLASSPATH":"$i"
  done
fi

echo "Using CLASSPATH:   $CLASSPATH"

nohup java $JAVA_OPTS \
    -classpath $CLASSPATH \
    com.gaoan.forever.App >  output 2>&1 &

if [ ! -z "run_forever_client_main.pid" ]; then
  echo $!> run_forever_client_main.pid
fi

