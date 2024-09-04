from ninja import NinjaAPI
from pydantic import BaseModel
from typing import List
from django.shortcuts import get_object_or_404

from myapp.models import Department, Employee

api = NinjaAPI()

class DepartmentSchema(BaseModel):
    id: int
    title: str

    class Config:
        from_attributes = True

class EmployeeSchema(BaseModel):
    id: int
    first_name: str
    last_name: str
    department_id: int
    birthdate: str = None
    cv: str = None

    class Config:
        from_attributes = True

class CreateDepartmentSchema(BaseModel):
    title: str

class CreateEmployeeSchema(BaseModel):
    first_name: str
    last_name: str
    department_id: int
    birthdate: str = None
    cv: str = None

@api.get("/departments", response=List[DepartmentSchema])
def list_departments(request):
    departments = Department.objects.all()
    return [DepartmentSchema.from_orm(department) for department in departments]

@api.post("/departments", response=DepartmentSchema)
def create_department(request, payload: CreateDepartmentSchema):
    department = Department.objects.create(**payload.dict())
    return DepartmentSchema.from_orm(department)

@api.get("/departments/{department_id}", response=DepartmentSchema)
def get_department(request, department_id: int):
    department = get_object_or_404(Department, id=department_id)
    return DepartmentSchema.from_orm(department)

@api.put("/departments/{department_id}", response=DepartmentSchema)
def update_department(request, department_id: int, payload: CreateDepartmentSchema):
    department = get_object_or_404(Department, id=department_id)
    for attr, value in payload.dict().items():
        setattr(department, attr, value)
    department.save()
    return DepartmentSchema.from_orm(department)

@api.delete("/departments/{department_id}")
def delete_department(request, department_id: int):
    department = get_object_or_404(Department, id=department_id)
    department.delete()
    return {"success": True}

@api.get("/employees", response=List[EmployeeSchema])
def list_employees(request):
    employees = Employee.objects.all()
    return [EmployeeSchema.from_orm(employee) for employee in employees]

@api.post("/employees", response=EmployeeSchema)
def create_employee(request, payload: CreateEmployeeSchema):
    employee = Employee.objects.create(**payload.dict())
    return EmployeeSchema.from_orm(employee)

@api.get("/employees/{employee_id}", response=EmployeeSchema)
def get_employee(request, employee_id: int):
    employee = get_object_or_404(Employee, id=employee_id)
    return EmployeeSchema.from_orm(employee)

@api.put("/employees/{employee_id}", response=EmployeeSchema)
def update_employee(request, employee_id: int, payload: CreateEmployeeSchema):
    employee = get_object_or_404(Employee, id=employee_id)
    for attr, value in payload.dict().items():
        setattr(employee, attr, value)
    employee.save()
    return EmployeeSchema.from_orm(employee)

@api.delete("/employees/{employee_id}")
def delete_employee(request, employee_id: int):
    employee = get_object_or_404(Employee, id=employee_id)
    employee.delete()
    return {"success": True}