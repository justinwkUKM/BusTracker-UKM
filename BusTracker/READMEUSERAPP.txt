The project structure is based on MVC Framework.
*Therefore, the database model files for USERAUTH, BUS, BUSSTOP, DRIVER, ROUTE are stored in the Model Package (folder). USERINSTANCE class is a model which determines each user session.
*The Controllers (Adapters) which manage data delivery to views are stored in the Controller package. Eg: BUSADAPTER, BUSSTOPADAPTER, ROUTEADAPTER.
*The Views (activities) which shows data on screen are stored in the view package. VOLLEYAPP class acts as controller for API REQUESTS to fetch the data and serve to models and views.
*A location listener SERVICE is activated when drivers starts a journey. It detects the driver position and logs, in background.



