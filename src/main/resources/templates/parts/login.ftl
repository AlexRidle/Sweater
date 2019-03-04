<#macro login path isRegisterForm>
    <#if !isRegisterForm>
    <h5>Please, log in!</h5>
    <#else>
    <h5>Registration</h5>
    </#if>
<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User Name :</label>
        <div class="col-sm-6">
            <input type="text" name="username" class="form-control" placeholder="User name"/>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control" placeholder="Password"/>
        </div>
    </div>
    <#if isRegisterForm>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control" placeholder="Email"/>
        </div>
    </div>
    </#if>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div class="form-group row ml-0">
        <button class="btn btn-primary" type="submit"><#if isRegisterForm>Create<#else>Sign In</#if></button>
        <div class="col-form-label ml-3">
         <#if !isRegisterForm><a href="/registration">Not registered?</a></#if>
        </div>
    </div>

</form>
</#macro>

<#macro logout>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <button class="btn btn-primary" type="submit">Sign Out</button>
</form>
</#macro>