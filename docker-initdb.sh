#!/bin/bash

SQL_FILES=("init_db.sql" "init_tables.sql" "init_permissions.sql" "init_super_admin.sql" "init_indexes.sql")
INIT_DIR="/app/initdb"

# Get the value of the DB_PATH environment variable from the container
DB_PATH=${DB_PATH}

# Check if DB_PATH variable does not exist or is empty
if [ -z "$DB_PATH" ]; then
  echo "Error: DB_PATH is not set, exiting..."
  exit 1
fi

# If database doesn't exist, create empty database file
if [ ! -f "$DB_PATH" ] || ! sqlite3 "$DB_PATH" "PRAGMA schema_version;" > /dev/null 2>&1; then
  echo "Database not found or invalid at $DB_PATH, creating new SQLite database..."

  sqlite3 "$DB_PATH" "PRAGMA user_version;" || {
    echo "Error: Failed to create database at $DB_PATH"
    exit 1
  }
fi

# Check existence of all SQL files before running
for file in "${SQL_FILES[@]}"; do
  sql_path="$INIT_DIR/$file"

  if [[ ! -f "$sql_path" ]]; then
    echo "Error: File $file not found in $INIT_DIR, exiting..."
    exit 1
  fi
done

# Merge all SQL files into transaction and save to temporary file
TMP_SQL=$(mktemp)
trap 'rm -f "$TMP_SQL"' EXIT
echo "BEGIN TRANSACTION;" > "$TMP_SQL"

for file in "${SQL_FILES[@]}"; do
  echo "Adding $file to transaction..."
  cat "$INIT_DIR/$file" >> "$TMP_SQL"
  echo "" >> "$TMP_SQL"
done

echo "COMMIT;" >> "$TMP_SQL"

# Run inside container safely
if ! sqlite3 "$DB_PATH" < "$TMP_SQL"; then
  echo "Error: Failed to execute SQL scripts"
  exit 1
fi

echo "All SQL scripts executed successfully"
