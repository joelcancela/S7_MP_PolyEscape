<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="polytech.teamf.api.PluginServices" %>
<%@ page import="polytech.teamf.services.Service" %>
<%@ page import="java.lang.reflect.InvocationTargetException" %>
<%@ page import="java.lang.reflect.Method" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="polytech.teamf.resources.PluginInstantiationResource" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="javax.ws.rs.core.GenericType" %>
<%@ page import="polytech.teamf.jarloader.JarLoader" %>
<%@ page import="polytech.teamf.plugins.MetaPlugin" %>
<%@ page import="java.util.Set" %>
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
            List<MetaPlugin> supportedPlugins = JarLoader.getInstance().getMetaPlugins();
        %>
        <p>Supported plugins list (up to date):</p>
        <ul class="w3-padding w3-ul w3-card-4" style="width:50%">
            <%
                for (MetaPlugin p : supportedPlugins) {
            %>
            <li class="w3-hover-opacity">
                <jsp:text>Plugin:</jsp:text>
                <%= p.getName()%>
            </li>
            <%
                }
            %>
        </ul>
    </div>

    <div class="w3-margin">
        <%
            Set<String> supportedServices = JarLoader.getInstance().getServicesClasses().keySet();
        %>
        <p>Supported services list (up to date):</p>
        <ul class="w3-padding w3-ul w3-card-4" style="width:50%">
            <%
                for (String service : supportedServices) {
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
        <footer class="w3-left"><strong>Team F -</strong> Doryan Bonifassi | Joël Cancela Vaz | Jérémy Lara |
            Nikita Rousseau
        </footer>
        <div class="w3-right"><strong>Current server session launch
            date:</strong> <%= request.getSession().getAttribute("sessionTime") %>
        </div>
    </div>
</div>
</body>
</html>
