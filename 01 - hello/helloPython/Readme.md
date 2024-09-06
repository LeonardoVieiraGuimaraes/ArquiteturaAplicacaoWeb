# Django Ninja Hello World

Este projeto demonstra como criar um simples "Hello World" usando Django Ninja.

## Passos para criar o projeto

1. **Instalar o Django e o Django Ninja:**
    ```sh
    pip install django django-ninja
    ```

2. **Criar um novo projeto Django:**
    ```sh
    django-admin startproject myproject
    cd myproject
    ```

3. **Criar um novo aplicativo Django:**
    ```sh
    python manage.py startapp myapp
    ```

4. **Configurar o Django Ninja:**

    No arquivo `myproject/settings.py`, adicione `myapp` à lista de `INSTALLED_APPS`:

    ```python
    INSTALLED_APPS = [
        ...
        'myapp',
    ]
    ```

    No arquivo `myapp/views.py`, adicione o seguinte código:
    ```python
    from ninja import NinjaAPI

    api = NinjaAPI()

    @api.get("/hello")
    def hello(request):
        return {"message": "Hello World"}
    ```

    No arquivo [`myproject/urls.py`](command:_github.copilot.openRelativePath?%5B%7B%22scheme%22%3A%22file%22%2C%22authority%22%3A%22%22%2C%22path%22%3A%22%2Fc%3A%2FGitHub%2FNewtonPaiva%2FArquiteturaAplicacaoWeb%2F01%20-%20hello%2FhelloPython%2Fmyproject%2Furls.py%22%2C%22query%22%3A%22%22%2C%22fragment%22%3A%22%22%7D%2C%22bc3f8785-af04-491b-be35-01d1d18823f0%22%5D "c:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\01 - hello\helloPython\myproject\urls.py"), adicione o seguinte código:
    ```python
    from django.contrib import admin
    from django.urls import path
    from myapp.views import api

    urlpatterns = [
        path('admin/', admin.site.urls),
        path('api/', api.urls),
    ]
    ```

5. **Executar o servidor de desenvolvimento:**
    ```sh
    python manage.py runserver
    ```

6. **Acessar o endpoint:**
    - Abra o navegador e vá para `http://127.0.0.1:8000/api/hello` para ver a mensagem "Hello World".