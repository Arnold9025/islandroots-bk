import os
import argparse

def create_entity_repo(entity_name, fields_str, base_package, id_type):
    # Convert package to path
    package_path = base_package.replace('.', '/')
    base_dir = f"src/main/java/{package_path}"
    
    entity_dir = os.path.join(base_dir, "entity")
    repo_dir = os.path.join(base_dir, "repository")
    
    os.makedirs(entity_dir, exist_ok=True)
    os.makedirs(repo_dir, exist_ok=True)
    
    # Simple field parsing (e.g., "String name, String email")
    fields = []
    if fields_str:
        for f in fields_str.split(','):
            parts = f.strip().split(' ')
            if len(parts) >= 2:
                fields.append((parts[0], parts[1]))
                
    # Generate Entity
    uuid_import = "import java.util.UUID;\n" if id_type == "UUID" else ""
    id_generation = '    @GeneratedValue(strategy = GenerationType.UUID)' if id_type == "UUID" else '    @GeneratedValue(strategy = GenerationType.IDENTITY)'

    entity_code = f"""package {base_package}.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
{uuid_import}
@Entity
@Table(name = "{entity_name.lower()}s")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class {entity_name} {{

    @Id
{id_generation}
    private {id_type} id;

"""
    for ftype, fname in fields:
        entity_code += f"    private {ftype} {fname};\n"
        
    entity_code += "}\n"
    
    entity_file = os.path.join(entity_dir, f"{entity_name}.java")
    with open(entity_file, "w") as f:
        f.write(entity_code)
        
    print(f"Created Entity: {entity_file}")
    
    # Generate Repository
    uuid_import_repo = "import java.util.UUID;\n" if id_type == "UUID" else ""
    repo_code = f"""package {base_package}.repository;

import {base_package}.entity.{entity_name};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
{uuid_import_repo}
@Repository
public interface {entity_name}Repository extends JpaRepository<{entity_name}, {id_type}> {{
}}
"""
    repo_file = os.path.join(repo_dir, f"{entity_name}Repository.java")
    with open(repo_file, "w") as f:
        f.write(repo_code)
        
    print(f"Created Repository: {repo_file}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Scaffold JPA Entity and Repository")
    parser.add_argument("--entity", required=True, help="Name of the Entity (e.g. User)")
    parser.add_argument("--fields", default="", help="Comma separated fields: 'String name, String email'")
    parser.add_argument("--base-package", required=True, help="Base package name (e.g. com.example.demo)")
    parser.add_argument("--id-type", default="Long", help="Type of the ID field (e.g. Long, UUID)")
    
    args = parser.parse_args()
    create_entity_repo(args.entity, args.fields, args.base_package, args.id_type)
