import os
import argparse

def create_controller(entity_name, base_package, id_type):
    # Convert package to path
    package_path = base_package.replace('.', '/')
    base_dir = f"src/main/java/{package_path}"
    
    controller_dir = os.path.join(base_dir, "controller")
    os.makedirs(controller_dir, exist_ok=True)
    
    var_name = entity_name[0].lower() + entity_name[1:]
    mapping_path = "/" + var_name + "s"
    
    # Generate REST Controller
    uuid_import = "import java.util.UUID;\n" if id_type == "UUID" else ""
    controller_code = f"""package {base_package}.controller;

import {base_package}.entity.{entity_name};
import {base_package}.service.{entity_name}Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
{uuid_import}
@RestController
@RequestMapping("/api{mapping_path}")
@RequiredArgsConstructor
public class {entity_name}Controller {{

    private final {entity_name}Service service;

    @PostMapping
    public ResponseEntity<{entity_name}> create(@RequestBody {entity_name} {var_name}) {{
        {entity_name} saved = service.save({var_name});
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }}

    @GetMapping("/{{id}}")
    public ResponseEntity<{entity_name}> getById(@PathVariable {id_type} id) {{
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }}

    @GetMapping
    public ResponseEntity<List<{entity_name}>> getAll() {{
        return ResponseEntity.ok(service.findAll());
    }}

    @PutMapping("/{{id}}")
    public ResponseEntity<{entity_name}> update(@PathVariable {id_type} id, @RequestBody {entity_name} {var_name}) {{
        return service.findById(id)
                .map(existing -> {{
                    // Update properties here based on request body
                    // Assuming id remains the same
                    {var_name}.setId(id);
                    return ResponseEntity.ok(service.save({var_name}));
                }})
                .orElse(ResponseEntity.notFound().build());
    }}

    @DeleteMapping("/{{id}}")
    public ResponseEntity<Void> delete(@PathVariable {id_type} id) {{
        return service.findById(id)
                .map(existing -> {{
                    service.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                }})
                .orElse(ResponseEntity.notFound().build());
    }}
}}
"""
    controller_file = os.path.join(controller_dir, f"{entity_name}Controller.java")
    with open(controller_file, "w") as f:
        f.write(controller_code)
        
    print(f"Created REST Controller: {controller_file}")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Scaffold REST Controller")
    parser.add_argument("--entity", required=True, help="Name of the Entity (e.g. User)")
    parser.add_argument("--base-package", required=True, help="Base package name (e.g. com.example.demo)")
    parser.add_argument("--id-type", default="Long", help="Type of the ID field (e.g. Long, UUID)")
    
    args = parser.parse_args()
    create_controller(args.entity, args.base_package, args.id_type)
