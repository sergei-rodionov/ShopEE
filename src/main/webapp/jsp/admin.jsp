<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<!-- for admin function -->
<script type="text/javascript">
    function hideAllAreas() {
        $("#admin_users_area").css("display", "none");
        $("#admin_category_area").css("display", "none");
        $("#admin_product_area").css("display", "none");
        $("#admin_storage_area").css("display", "none");
        $("#admin_cart_area").css("display", "none");
    }


    <!-- user list for edit -->
    function admin_user_list() {
        hideAllAreas();
        var urladm = "${pageContext.request.contextPath}/admin_user_list.json";
        $.getJSON(urladm, function (datap) {
            var bodyData = "";
            $(datap).each(function () {
                var roles = [];
                $.each(this.roles, function (i, prop) {
                    roles.push(prop.role + "<br/>");
                });
                var btnChangeStat = "Enable";
                var labelStat = "-";
                if (this.isenable == "true") {
                    btnChangeStat = "Disable";
                    labelStat = "+";
                }
                bodyData += "<tr><td>" + labelStat + "&nbsp;" + this.username + "</td><td>" + roles.join("") + "</td><td>" +
                "<a href='#' onclick='admin_removeUser(\"" + this.username +
                "\")'>Delete</a>&nbsp;<a href='#' onclick='admin_changeUserPassword(\"" + this.username +
                "\")'>ChangePass</a>&nbsp;<a href='#' onclick='admin_switchUserStatus(\"" + this.username +
                "\")'>" + btnChangeStat + "</a>" +
                "</td></tr>";
            });
            $("#admin_users_list").html("<table border='1'><tr><th>Username</th><th>Roles</th><th>Operation</th></tr>" +
            bodyData + "</table><br/>");
        });

        $("#admin_users_area").css("display", "inline");
    }

    function admin_addNewUser() {
        $("#errNewUserName").text($("#newusername").val().length == 0 ? "Wrong user name!" : "");
        $("#errNewPass").text($("#newpassword").val().length == 0 ? "Wrong password!" : "");
        $("#errNewRoles").text($("#newroles").val().length == 0 ? "Wrong roles!" : "");
        if (($("#newusername").val().length == 0) ||
                ($("#newpassword").val().length == 0) ||
                ($("#newroles").val().length == 0)) {
            return;
        }
        var newUserData = {
            "newusername": $("#newusername").val(),
            "newpassword": $("#newpassword").val(),
            "newroles": $("#newroles").val()
        };
        $.ajax({
            url: '${pageContext.request.contextPath}/admin_user_add.json',
            type: 'POST',
            data: newUserData, dataType: "json",
            async: false,
            cache: false,
            timeout: 30000,
            error: function () {
                return true;
            },
            complete: admin_user_list
        });
    }

    function admin_removeUser(username) {
        if (confirm('Delete user?')) {
            <!-- delete user -->
            $.ajax({
                url: '${pageContext.request.contextPath}/admin_user_delete.json',
                type: 'GET',
                data: {username: username}, dataType: "json",
                async: false,
                cache: false,
                timeout: 30000,
                error: function () {
                    return true;
                },
                complete: admin_user_list
            });
        }
    }

    function admin_changeUserPassword(username) {
        var newPassword = prompt("Enter new password (not blank):", '');
        if (newPassword == "") {
            alert("Password not change!");
            return;
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/admin_user_changepass.json',
            type: 'POST',
            data: {username: username, userpass: newPassword}, dataType: "json",
            async: false,
            cache: false,
            timeout: 30000,
            error: function () {
                return true;
            },
            complete: admin_user_list
        });
    }

    function admin_switchUserStatus(username) {
        $.ajax({
            url: '${pageContext.request.contextPath}/admin_user_switchstatus.json',
            type: 'GET',
            data: {username: username}, dataType: "json",
            async: false,
            cache: false,
            timeout: 30000,
            error: function () {
                return true;
            },
            complete: admin_user_list
        });
    }

    function admin_categories_list() {
        hideAllAreas();
        var urladm = "${pageContext.request.contextPath}/admin_categories_list.json";
        $.getJSON(urladm, function (datap) {
            var bodyData = "";
            $(datap).each(function () {
                bodyData += "<tr><td>" + this.categoryId + "</td><td>" + this.categoryName + "</td><td>" +
                "<a href='#' onclick='admin_detele_category(" + this.categoryId + ")'>Delete</a>&nbsp;" +
                "<a href='#' onclick='admin_changeCategory(" + this.categoryId + ")'>Change</a></td><tr>";
            });
            $("#admin_category_list").html("<table border='1'><tr><th colspan='3'>Categories</th></tr>" +
            bodyData + "</table>");
        });
        $("#admin_category_area").css("display", "inline");
    }

    function admin_detele_category(categoryId) {
        if (confirm('Delete category?')) {
            $.ajax({
                url: '${pageContext.request.contextPath}/admin_category_delete.json',
                type: 'GET',
                data: {categoryId: categoryId}, dataType: "json",
                async: false,
                cache: false,
                timeout: 30000,
                error: function () {
                    return true;
                },
                complete: admin_categories_list
            });
        }
    }

    function admin_addCategory() {
        $("#errNewCategory").text($("#newcategory").val().length == 0 ? "Wrong name!" : "");
        if ($("#newcategory").val().length == 0) {
            return;
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/admin_category_add.json',
            type: 'POST',
            data: {newcategory: $("#newcategory").val()}, dataType: "json",
            async: false,
            cache: false,
            timeout: 30000,
            error: function () {
                return true;
            },
            complete: admin_categories_list
        });
    }

    function admin_changeCategory(categoryId) {
        var newName = prompt("Enter new name category (not blank):", '');
        if (newName == "") {
            alert("Category not change!");
            return;
        }
        $.ajax({
            url: '${pageContext.request.contextPath}/admin_category_change.json',
            type: 'POST',
            data: {categoryId: categoryId, newName: newName}, dataType: "json",
            async: false,
            cache: false,
            timeout: 30000,
            error: function () {
                return true;
            },
            complete: admin_categories_list
        });
    }

    function admin_products_list(categoryId){
        hideAllAreas();
        var urladm = "${pageContext.request.contextPath}/admin_products_list.json?categoryId="+categoryId;

        $.getJSON(urladm, function (datap) {
            var bodyData = "";
            $(datap).each(function () {
                bodyData += "<tr><td>" + this.id + "</td><td>" + this.name + "</td><td>" +
                "<a href='#' onclick='admin_product_delete(" + this.id + ")'>Delete</a>&nbsp;" +
                "<a href='#' onclick='admin_product_edit(" + this.id + ")'>Change</a></td><tr>";
            });
            $("#admin_product_list").html("<table border='1'><tr><th colspan='3'>Products</th></tr>" +
            bodyData + "</table>");
        });

        $("#admin_product_area").css("display", "inline");
    }

    function admin_product_edit(productId) {
        <!-- get all categories -->
        $.getJSON("${pageContext.request.contextPath}/categories.json", function (datac) {
            var itemsc = [];
            $(datac).each(function (idx) {
                itemsc.push('<option value="' + this.id + '">' + this.name + '</option>');
            });
            $('#fldCategory').empty().append(itemsc.join(""));
        });

        var urladm = "${pageContext.request.contextPath}/admin_products_edit.json?productId="+productId;
        $.getJSON(urladm, function (datap) {
            $(datap).each(function() {
                $("#fldProductId").val(this.id);
                $("#fldProductName").val(this.name);
                $("#fldCategory").val(this.categoryId);

                $("input[id*=specId]").val("");
                $("input[id*=specName]").val("");
                $("input[id*=specValue]").val("");
                $.each(this.specifics, function(i,prop) {
                    $("#specId"+i).val(prop.specificId);
                    $("#specName"+i).val(prop.specificName);
                    $("#specValue"+i).val(prop.specificValue);
                });
            });
        });
    }

    function admin_product_save() {
        alert("SAVE");
        $("#errProductName").text($("#fldProductName").val().length == 0 ? "Wrong name!" : "");
        var productData = {
            "id": $("#fldProductId").val(),
            "name": $("#fldProductName").val(),
            "category": $("#fldCategory").val(),
            "specifics":[]};

        for(var i=0; i<5; i++) {
            if ($("#specName"+i).val().length != 0) {
                var prodSpec = {
                    "id": $("#specId"+i).val(),
                    "specificName": $("#specName"+i).val(),
                    "specificValue": $("#specValue"+i).val()
                };
                productData.specifics.push(prodSpec);
            }
        }

        $.ajax({
            url: '${pageContext.request.contextPath}/admin_product_save.json',
            type: 'POST',
            data: JSON.stringify(productData), dataType: "json",
            contentType: 'application/json',
            async: false,
            cache: false,
            timeout: 30000,
            error: function () {
                return true;
            },
            complete: admin_products_list($("#fldCategory").val())
        });

    }
</script>


<div class="bodyShop">
    <div class="bodyMenu">
         <a href="${pageContext.request.contextPath}/" class="pMenuAdmin">Home page</a>

        <p class="pMenuCategory">Admin menu</p>
        <ul class="ulMenu" id="ulAdminMenu">
            <!-- category or menu list -->
            <li><a class="listCat" href="#" onclick="admin_user_list()">Users</a></li>
            <li><a class="listCat" href="#" onclick="admin_categories_list()">Categoris</a></li>
            <li><a class="listCat" href="#" onclick="admin_products_list('all')">Products/Storage</a></li>
            <li><a class="listCat" href="#" onclick="">Cart/Orders</a></li>
        </ul>
    </div>
    <div class="bodyMain">
        <div class="bodyHead" id="bodyAdminHead">All</div>
        <div class="divBodyList" id="bodyAdminList">
            <!-- main data list -->
            <div id="admin_users_area" style="display: none">
                <div id="admin_users_list">
                </div>
                <br/>
                <table border="1">
                    <tr>
                        <th colspan="2">New user</th>
                    </tr>
                    <tr>
                        <td>User name:</td>
                        <td><input type="text" id="newusername"><span id="errNewUserName"></span></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" id="newpassword"><span id="errNewPass"></span></td>
                    </tr>
                    <tr>
                        <td>Roles:<br/>ROLE_USER<br/>ROLE_ADMIN<br/>Separate:space</td>
                        <td><input type="text" id="newroles"><span id="errNewRoles"></span></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="button" value="Add" onclick="admin_addNewUser()"></td>
                    </tr>
                </table>
            </div>
            <div id="admin_category_area" style="display: none">
                <div id="admin_category_list">
                </div>
                * Delete category if empty.<br/><br/>
                <table border="1">
                    <tr>
                        <th colspan="2">New category</th>
                    </tr>
                    <tr>
                        <td>Category name:</td>
                        <td><input type="text" id="newcategory"><span id="errNewCategory"></span></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="button" value="Add" onclick="admin_addCategory()"></td>
                    </tr>
                </table>
            </div>
            <div id="admin_product_area" style="display: none">
                <div id="admin_product_list"></div><br/>
                <table border="1"><tr><th colspan="2">New/edit product</th></tr>
                    <tr><td>Product name:</td><td>
                        <input type="hidden" id="fldProductId">
                        <input type="text" id="fldProductName"><span id="errProductName"></span></td></tr>
                    <tr><td>Category:</td><td><select id="fldCategory"></select></td></tr>
                    <tr><td colspan="2"><div><div>
                        <table border="1"><tr><th colspan="2">Specifics</th></tr>
                            <tr><th>Name</th><th>Value</th></tr>
                            <tr><td>
                                <input type="hidden" id="specId0">
                                <input type="text" id="specName0"></td><td>
                                <input type="text" id="specValue0"></td></tr>
                            <tr><td>
                                <input type="hidden" id="specId1">
                                <input type="text" id="specName1"></td><td>
                                <input type="text" id="specValue1"></td></tr>
                            <tr><td>
                                <input type="hidden" id="specId2">
                                <input type="text" id="specName2"></td><td>
                                <input type="text" id="specValue2"></td></tr>
                            <tr><td>
                                <input type="hidden" id="specId3">
                                <input type="text" id="specName3"></td><td>
                                <input type="text" id="specValue3"></td></tr>
                            <tr><td>
                                <input type="hidden" id="specId4">
                                <input type="text" id="specName4"></td><td>
                                <input type="text" id="specValue4"></td></tr>
                        </table></div><div>
                        <table border="1">
                            <tr><th colspan="2">Storage</th></tr>
                            <tr><td>Quantity</td><td><input type="text" id="fldProductQty"></td></tr>
                            <tr><td>Price (1 pcs)</td><td><input type="text" id="fldProductPrice"></td></tr>
                        </table>
                    </div></div>
                    </td></tr>
                    <tr><td colspan="2"><input type="button" onclick="admin_product_save()" value="Save"></td></tr>
                </table>
            </div>
            <div id="admin_cart_area" style="display: none">
                cart
            </div>
        </div>
    </div>
</div>




<%@ include file="bottom.jsp" %>