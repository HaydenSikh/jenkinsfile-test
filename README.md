# jenkinsfile-test
Testing out the DSL for Jenkin's pipeline plugin and the Jenkinsfile

## Set up

1. Create a container
  
    ```
    docker run -p 8080:8080 -p 8081:8081 -p 8022:22 -ti jenkinsci/workflow-demo
    ```
    
2. Install required plugins
  
  - ANSI Color
  - Timestamper
  - Checkstyle
  - Task Scanner
  - Warnings

3. Add a new Pipeline job which checks this repo.


## Noted Limitations

- The Cobertura plugin has not been integrated with the Pipeline plugin, can't be called.
- Most static analysis plugins don't render their graphs of results any more.
- No way easy to rerun a stage with out doing a Pipeline "replay" and modifying the pipeline script.