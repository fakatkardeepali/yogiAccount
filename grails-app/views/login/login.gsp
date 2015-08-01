<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Yogi Account</title>
    %{--<script src="http://maps.google.com/maps/api/js?sensor=false"></script>--}%
    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>

</head>

<body class="body-wide body-auth">
%{--<div class=" ">--}%
<div id="content" class="content-container">

    %{--<section class="view-container">--}%
    <div class="page-signin">
        <div class="signin-header">
            <section class="logo text-center">
                <h1><a href="#/">Yogi Account</a></h1>
            </section>
        </div>

        <div class="signin-body">
            <div class="container">
                <div class="form-container">
                    <div style="color: #B80638">${msg}</div>
                    <g:form class="form-horizontal" controller="login">
                        <fieldset>
                            <div class="form-group">
                                <div class="input-group input-group-first">
                                    <span class="input-group-addon">
                                        <span class="ti-email"></span>
                                    </span>
                                    <input type="email" name="j_username"
                                           class="form-control input-lg"
                                           placeholder="Username">

                                </div>
                            </div>

                            <div class="form-group">
                                <div class="input-group">
                                    <span class="input-group-addon">
                                        <span class="ti-lock"></span>
                                    </span>
                                    <input type="password" name="j_password"
                                           class="form-control input-lg"
                                           placeholder="Password">
                                </div>
                            </div>

                            <div class="divider divider-xl"></div>

                            <div class="form-group">
                                <g:actionSubmit name="Login" class="btn-primary btn-lg btn-block text-center"
                                                action="auth"
                                                value="${message(code: 'page.login.label', default: 'Log In')}"/>
                            </div>
                        </fieldset>
                    </g:form>

                    <div class="divider"></div>
                    <section class="additional-info">
                        <p class="text-right">
                            <a href="#/pages/forgot-password">Forgot your password?</a>
                            <span>|</span>
                            <a href="#/pages/signup">Sign up</a>
                        </p>
                    </section>

                </div>
            </div>
        </div>

    </div>
    %{--</section>--}%
</div>
%{--</div>--}%

</body>
</html>