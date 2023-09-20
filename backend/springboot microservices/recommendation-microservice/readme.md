### virtual envirenement
```
python -m venv venv
venv\Scripts\activate
```
> then the virtual envirenement will be activated

### generate requirements file
pip freeze > requirements.txt

### running the server
python -m uvicorn main:app --reload