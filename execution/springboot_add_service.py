import os
import argparse

def create_service(entity_name, base_package, id_type):
    # Convert package to path
    package_path = base_package.replace('.', '/')
    base_dir = f"src/main/java/{package_path}"
    
    service_dir = os.path.join(base_dir, "service")
    impl_dir = os.path.join(service_dir, "impl")
    
    os.makedirs(service_dir, exist_ok=True)
    os.makedirs(impl_dir, exist_ok=True)
    
    # Generate Service Interface
    uuid_import = "import java.util.UUID;\n" if id_type == "UUID" else ""
    service_code = f"""package {base_package}.service;

import {base_package}.entity.{entity_name};
import java.util.List;
import java.util.Optional;
{uuid_import}
public interface {entity_name}Service {{
    {entity_name} save({entity_name} entity);
    Optional<{entity_name}> findById({id_type} id);
    List<{entity_name}> findAll();
    void deleteById({id_type} id);
}}
"""
    service_file = os.path.join(service_dir, f"{entity_name}Service.java")
    with open(service_file, "w") as f:
        f.write(service_code)
        
    print(f"Created Service Interface: {service_file}")
    
    # Generate Service Implementation
    impl_code = f"""package {base_package}.service.impl;

import {base_package}.entity.{entity_name};
import {base_package}.repository.{entity_name}Repository;
import {base_package}.service.{entity_name}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
{uuid_import}
@Service
@RequiredArgsConstructor
@Transactional
public class {entity_name}ServiceImpl implements {entity_name}Service {{

    private final {entity_name}Repository repository;

    @Override
    public {entity_name} save({entity_name} entity) {{
        return repository.save(entity);
    }}

    @Override
    @Transactional(readOnly = true)
    public Optional<{entity_name}> findById({id_type} id) {{
        return repository.findById(id);
    }}

    @Override
    @Transactional(readOnly = true)
    public List<{entity_name}> findAll() {{
        return repository.findAll();
    }}

    @Override
    public void deleteById({id_type} id) {{
        repository.deleteById(id);
    }}
}}
"""
    impl_file = os.path.join(impl_dir, f"{entity_name}ServiceImpl.java")
    with open(impl_file, "w") as f:
        f.write(impl_code)
        
    print(f"Created Service Implementation: {impl_file}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Scaffold Service Interface and Implementation")
    parser.add_argument("--entity", required=True, help="Name of the Entity (e.g. User)")
    parser.add_argument("--base-package", required=True, help="Base package name (e.g. com.example.demo)")
    parser.add_argument("--id-type", default="Long", help="Type of the ID field (e.g. Long, UUID)")
    
    args = parser.parse_args()
    create_service(args.entity, args.base_package, args.id_type)
