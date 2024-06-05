#!/usr/bin/env bash

# Trap SIGINT signal (Ctrl-C)
trap exit 1 SIGINT

# Cleanup
rm -r Build
mkdir Build

# Find all subdirectories containing gradlew script
for dir in */
do
    # Navigate into the directory
    cd "${dir}" || continue

    # Check if gradlew script exists
    if [ -f "gradlew" ]
    then
        # Strip trailing /
        dir=${dir%*/}

        # Detect java version
        case $dir in
#             1.20)
#                 export PATH="/usr/lib/jvm/java-21-openjdk/bin/:$PATH"
#                 ;;
            *)
                export PATH="/usr/lib/jvm/java-17-openjdk/bin/:$PATH"
                ;;
        esac

        # Grant execute permission to gradlew script
        chmod +x gradlew

        # Clean and build using gradlew
        ./gradlew clean build

        # Loop over loader directories for build directories
        for loader in fabric forge neoforge quilt
        do
            # Navigate into the directory
            cd "${loader}" || continue

            # Get the built JAR file
            jar_file=$(find build/libs -name "killeffects-${loader}-*.jar" -type f)

            # Extract version number from the JAR file name
            version=$(basename "$jar_file" | grep -oP "killeffects-${loader}-\K[0-9]+\.[0-9]+\.[0-9]+")

            # Move the built JAR file to the specified directory
            mv build/libs/killeffects-${loader}-"${version}".jar ../../Build/KillEffects-"${loader}-${dir,,}-${version}".jar

            # Navigate back to the parent directory
            cd ..

        done

        # Cleanup
        rm -rf .gradle .idea .vscode build logs run
    fi

    # Navigate back to the parent directory
    cd ..
done
