    #!/bin/bash
    for i in {1..60}; do
      /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P S@omePassword12345! -Q "SELECT 1" && break
      echo "Waiting for SQL Server to be available..."
      sleep 2
    done
    /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P S@omePassword12345! -d master -i init.sql
    