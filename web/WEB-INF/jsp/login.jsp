<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <style type='text/css'>
            body {
                padding-top: 0px;
                margin-left: 0px;
                margin-right: 0px;
                margin-top: 0px;
                background-color: whitesmoke;

            }

            p {
                box-sizing: border-box;
                color: #FFF;
                font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
                font-size: 16px;
                font-weight: 500;
                margin-bottom: 0px;
                margin-top: 0px;

            }

            #heading {
                text-align: left;
                margin: 0px auto;
                padding: 15px;
                background: none repeat scroll 0% 0% #337AB7;
                border-radius: 0px 0px 0px 0px;
                position: relative;

            }

            #centerbox {
                border-radius: 4px 4px 4px 4px;
                border: 1px solid #337AB7;
                width: 460px;
                background-color: white;
                margin-top: 90px;
                left: 50%;
                margin-left: -230px;
                position: absolute;
                overflow: visible;
                text-align: right;
                line-height: 1;

            }

            #login {

                padding: 10px;

            }


            input.button {
                color: #FFF;
                background-color: #337AB7;
                border: 1px solid #2E6DA4;
                border-radius: 3px;
                display: inline;
                width: auto;
                padding: 5px;
            }

            :focus {
                border-color: #66AFE9;
                outline: 0px none;
                box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.075) inset, 0px 0px 8px rgba(102, 175, 233, 0.6);
            }
            input {
                font-size: 14px;
                margin: 5px;
                line-height: 1.42857;
                color: #555;
                display: block;
                width: 95%;
                background: #FFF none;
                padding: 5px;
                border: 1px solid #CCC;
                border-radius: 4px;
                box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.075) inset;
                transition: border-color 0.15s ease-in-out 0s, box-shadow 0.15s ease-in-out 0s;
            }
            input.button:hover {
                box-shadow: 0px 1px 3px rgba(50, 50, 50, 0.75);
                background-color: #286090;
                border-color: #204D74;
                border-radius: 3px;
                color: white;

            }

            #logo_img {
                position: absolute;
                height: 15px;
                width: 50px;
                left: 50%;
                margin-left: -50px;
                overflow: visible;

            }
        </style>
    </head>

    <body>
        <div id='centerbox'>
            <div id='heading'>

                <p>
                    Iniciar sesión
                </p></div>

            <div id="login">

                <form action='login' method='post'><input type='hidden' name='action' value='login'>

                    <p><input type='text' name='username' placeholder="Usuario"></p>

                    <p><input type='password' name='password' placeholder="Contrase&ntilde;a">

                    <p/>
                    <input type='submit' class='button' value='Autenticarse'>
                    <c:if test="${param.error != null}">        
                        <p style="color: #C9302C; text-align: center">
                            Credenciales no válidas. Intente otra vez.
                        </p>
                    </c:if>
                </form>

            </div>
        </div>

    </body>
</html>