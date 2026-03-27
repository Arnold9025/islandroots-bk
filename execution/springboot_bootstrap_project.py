import os
import subprocess
import argparse
import urllib.request
import urllib.parse
import zipfile
import io
import ssl

def bootstrap(group, artifact, name, description, package_name, dependencies):
    print(f"Bootstrapping Spring Boot project: {name}...")
    
    # Base URL for Spring Initializr
    base_url = "https://start.spring.io/starter.zip"
    
    # Query parameters
    params = {
        "type": "maven-project",
        "language": "java",
        "baseDir": ".",
        "groupId": group,
        "artifactId": artifact,
        "name": name,
        "description": description,
        "packageName": package_name,
        "packaging": "jar",
        "javaVersion": "17",
        "dependencies": dependencies
    }
    
    url = f"{base_url}?{urllib.parse.urlencode(params)}"
    print(f"Downloading from: {url}")
    
    # Use unverified SSL context for macOS python environments
    ctx = ssl.create_default_context()
    ctx.check_hostname = False
    ctx.verify_mode = ssl.CERT_NONE
    
    # Download the zip file
    req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
    try:
        with urllib.request.urlopen(req, context=ctx) as response:
            with zipfile.ZipFile(io.BytesIO(response.read())) as z:
                z.extractall(".")
        print("Project bootstrapped successfully!")
    except Exception as e:
        print(f"Error bootstrapping project: {e}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Bootstrap a Spring Boot project.")
    parser.add_argument("--group", default="com.example", help="Group ID")
    parser.add_argument("--artifact", default="demo", help="Artifact ID")
    parser.add_argument("--name", default="demo", help="Project name")
    parser.add_argument("--description", default="Demo project for Spring Boot", help="Project description")
    parser.add_argument("--package-name", default="com.example.demo", help="Package Name")
    parser.add_argument("--dependencies", default="web,data-jpa,postgresql,lombok,validation", help="Comma-separated dependencies")
    
    args = parser.parse_args()
    bootstrap(args.group, args.artifact, args.name, args.description, args.package_name, args.dependencies)
