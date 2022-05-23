build() {
    echo "Producer is building..."
    mvn clean package
}

test() {
    echo "Producer is testing..."
    mvn test
}

run() {
    echo "Producer is running..."
    ENV_FILE=./.env
    if [ -f "$ENV_FILE" ]; then
        export $(grep -vE "^(#.*|\s*)$" .env) && mvn exec:java -Dexec.mainClass="org.kybprototyping.producer.App"
    else
        echo ".env does not exist!"
    fi
}

if [ "$1" = "build" ] && [ "$2" = "run" ]; then
    build && run
elif [ "$1" = "build" ]; then
    build
elif [ "$1" = "test" ]; then
    test
elif [ "$1" = "run" ]; then
    run
else
    echo "Usage: bash .sh [FLAGS]"
    echo
    echo "Flags:"
    echo "run    Use to run the producer"
    echo
    exit 1
fi
