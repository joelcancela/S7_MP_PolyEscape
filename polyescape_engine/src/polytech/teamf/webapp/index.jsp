<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="polytech.teamf.api.PluginServices" %>
<%@ page import="polytech.teamf.services.Service" %>
<%@ page import="java.lang.reflect.InvocationTargetException" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

<%
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formatDateTime = now.format(formatter);
    if (request.getSession().getAttribute("sessionTime") == null) {
        request.getSession().setAttribute("sessionTime", formatDateTime);
    }
%>

<html class="w3-light-gray w3-text-black">
<head>
    <title>PolyEscape Back End Server</title>
</head>
<body>
<div class="w3-container w3-light-blue w3-hover-grayscale w3-jumbo" style="text-align: center"
     onClick="window.location.reload()">
    <h1>Escape Game Corporation (Back End Server)</h1>
</div>

<div class="w3-margin w3-card" style="background-color: #FAFAFA">
    <header class="w3-container w3-gray w3-hover-opacity w3-xxlarge" style="text-align: center">Engine data</header>
    <div class="w3-margin">
        <%
            JSONArray supportedPlugins = new JSONArray();
            try {
                supportedPlugins = new JSONArray(new PluginServices().getAllStepsType());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        %>
        <p>Supported plugins list (up to date):</p>
        <ul class="w3-padding w3-ul w3-card-4" style="width:50%">
            <%
                for (int i = 0; i < supportedPlugins.length(); ++i) {
                    JSONObject pluginData = (JSONObject) supportedPlugins.get(i);
            %>
            <li class="w3-hover-opacity">
                <jsp:text>Plugin:</jsp:text>
                <%= pluginData.get("type")%>
                <jsp:text> (</jsp:text>
                <%= pluginData.get("name")%>
                <jsp:text>)</jsp:text>
            </li>
            <%
                }
            %>
        </ul>
    </div>

    <div class="w3-margin">
        <%
            JSONArray supportedServices = new JSONArray();
            try {
                PluginServices pluginServices = new PluginServices();
                Method method = pluginServices.getClass().getDeclaredMethod("getClasses", String.class);
                method.setAccessible(true);
                Class[] services = (Class[]) method.invoke(pluginServices, "polytech.teamf.services");
                for (Class c : services) {
                    if (Service.class.isAssignableFrom(c) && c != Service.class) {
                        supportedServices.put(c.getSimpleName());
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        %>
        <p>Supported services list (up to date):</p>
        <ul class="w3-padding w3-ul w3-card-4" style="width:50%">
            <%
                for (int i = 0; i < supportedServices.length(); ++i) {
                    String service = supportedServices.getString(i);
            %>
            <li class="w3-hover-opacity">
                <jsp:text>Service:</jsp:text>
                <%= service%>
            </li>
            <%
                }
            %>
        </ul>
    </div>

    <div class="w3-container w3-gray w3-hover-opacity">
        <footer class="w3-left"><strong>Team F -</strong> Dorian Bonifassi | Joël Cancela Vaz | Jérémy Lara |
            Nikita Rousseau
        </footer>
        <div class="w3-right"><strong>Current server session launch
            date:</strong> <%= request.getSession().getAttribute("sessionTime") %>
        </div>
    </div>
</div>
</body>
</html>
