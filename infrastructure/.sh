deploy() {
    echo "AWS deployment is starting..."
    export $(grep -vE "^(#.*|\s*)$" ../.env) && cdk bootstrap && cdk deploy --all --require-approval never
}

if [ "$1" = "deploy" ]; then
    deploy
else
    echo "Usage: bash .sh [FLAGS]"
    echo
    echo "Flags:"
    echo "deploy   Use to deploy the AWS stacks"
    echo
    exit 1
fi
