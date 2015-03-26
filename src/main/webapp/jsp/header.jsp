<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta charset="utf-8">
    <title>ShopEE - online shop</title>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<c:url value="/js/jquery-2.1.3.min.js"/>"></script>
    <script type="text/javascript">
        var SELECT_ALL_CATEGORY = "All";
        <!-- name of all categories -->
        var currentNameCategory = SELECT_ALL_CATEGORY;
        var currentCartId = "";

        $(document).ready(function () {

            $.getJSON("${pageContext.request.contextPath}/categories.json", function (datac) {
                var itemsc = [];
                $(datac).each(function (idx) {
                    itemsc.push('<li><a id="cat' + this.id + '" class="listCat" href="#">' + this.name + '</a></li>');
                });
                $('#ulCategory').append(itemsc.join(""));

            });

            <!-- onclick select category -->
            $("#ulCategory").on("click", ".listCat", function () {
                var categoryId = $(this).attr("id").replace("cat", "");
                currentNameCategory = $(this).text();
                getProductsByCategory(categoryId);
            });

            currentNameCategory = SELECT_ALL_CATEGORY;
            <!-- first fill page -->
            getProductsByCategory(SELECT_ALL_CATEGORY);

            <sec:authorize access="isAuthenticated()">
            updateCountViewCart();
            </sec:authorize>
        });

        function getProductsByCategory(i) {
            var urlprod = "${pageContext.request.contextPath}/products.json?category=";
            if (i == SELECT_ALL_CATEGORY) {
                urlprod += "all";
            } else {
                urlprod += i;
            }

            $.getJSON(urlprod, function (datap) {
                var items = [];
                $(datap).each(function () {
                    var line =
                    '<div class="rowProduct">'+
                    '<a href="#" onclick="getProductById(' + this.id +')"><div class="rowProductName">' + this.name + '</div></a>'+
                    '<div class="rowProductDetails"></div>'+
                    '<div class="rowProductBtn">';
                    <sec:authorize access="isAuthenticated()">
                        line+='<input type="button" onclick="addToCart(' + this.id + ')" value="add Cart">&nbsp;&nbsp;';
                    </sec:authorize>
                    line+='Price: ' + this.price + ' RUB</div></div>';
                    items.push(line);
                });
                $("#bodyProductsList").html(items.join(""));
            });
            $("#bodyProductsHead").text(currentNameCategory);
        }

        function getProductById(prodId) {
            var urlprod = "${pageContext.request.contextPath}/product_details.json?productId=" + prodId;

            $.getJSON(urlprod, function (datap) {
                var items = [];
                $(datap).each(function () {
                    var spec = "";
                    $.each(this.product.specifics, function (i, prop) {
                        spec += prop.name + " : " + prop.value + "<br/>";
                    });
                    var line =
                            '<div class="rowProduct">'+
                            '<div class="rowProductName">' + this.product.name + '</div>'+
                            '<div class="rowProductDetails">'+spec+'</div>'+
                            '<div class="rowProductBtn">';
                    <sec:authorize access="isAuthenticated()">
                    line+='<input type="button" onclick="addToCart(' + this.id + ')" value="add Cart">&nbsp;&nbsp;';
                    </sec:authorize>
                    line+='Price: ' + this.price + ' RUB (available: '+this.quantity+')</div></div>';
                    items.push(line);
                });
                $("#bodyProductsList").html(items.join(""));
            });

            $("#bodyProductsHead").text("Details product");

        }

        function addToCart(prodId) {
            <sec:authorize access="!isAuthenticated()">
            alert("Need login!");
            </sec:authorize>
            <sec:authorize access="isAuthenticated()">
            var urlprod = "${pageContext.request.contextPath}/addtocart.json?productId=" + prodId + "&cartId=" + currentCartId;
            $.getJSON(urlprod)
                    .done(function (datap) {
                        $(datap).each(function () {
                            currentCartId = this.cartId;
                            $(".cart").text("Cart (" + this.cartQty + ")");
                        });
                    });
            </sec:authorize>
        }

        <sec:authorize access="isAuthenticated()">
        <!-- only Authorized section -->
        function openCart() {
            var urlmain = "${pageContext.request.contextPath}/cart_details.json?cartId=" + currentCartId;
            $.getJSON(urlmain, function (datap) {
                var cartResult = "";
                $(datap).each(function () {
                    var items = "";
                    $.each(this.cartItems, function (i, prop) {
                        items += '<tr><td>' + prop.itemProd + '</td><td><input name="prodQty'+prop.itemProdId+
                        '" type="text" size="5" value="' + prop.itemQty+'"></td><td>' + prop.itemPrice +
                        ' RUB</td><td><input type="button" onclick="changeItemCart(' + prop.itemProdId + ',0)" value="Delete"></td></tr>';
                    });

                    if (this.cartQty != 0) {
                        cartResult = '<div class="rowProduct">' +
                        '<div class="rowProductName">Current order</div>' +
                        '<div class="rowProductDetails">' +
                        '<table border="0" class="tblCart">' +
                        '<tr><th>Product</th><th>Quantity</th><th>Price</th><th>Operation</th></tr>' +
                        items +
                        '</table>' +
                        '</div>' +
                        '<div class="rowProductBtn">Total<br/>ID: ' + this.cartId + ', Date:' + this.cartDate +
                        ', <br/>Comments: <br><textarea cols="40" rows="5" id="commet' + this.cartId + '" >"' + this.cartComment +
                        '"</textarea><br/><br/> - quantity: ' + this.cartQty + '<br> - amount: ' + this.cartSum + ' RUB<br/>' +
                        '<input type="button" value="Update">&nbsp;<input type="button" value="Pay"></div></div>';
                    } else {
                        cartResult = '<div class="rowProduct">Cart is empty</div>';
                    }
                });
                $('#bodyProductsList').html(cartResult);
            });
        }

        function changeItemCart(prodID, newQty) {
            var urlmain = "${pageContext.request.contextPath}/change_cart.json?cartId=" + currentCartId + "&productId=" + prodID + "&quantity=" + newQty;
            $.getJSON(urlmain, function (datap) {

            });
            openCart();
            updateCountViewCart();
        }

        function updateCountViewCart() {
            $.getJSON("${pageContext.request.contextPath}/restore_cart.json", function (datac) {
                currentCartId = datac.cartId;
                if (datac.cartQty == 0) {
                    $(".cart").text("Cart");
                } else {
                    $(".cart").text("Cart (" + datac.cartQty + ")");
                }
            });
        }



        </sec:authorize>
    </script>

</head>
<body>

<div class="headerShop">
    <div class="headerLogo">
        <a href="${pageContext.request.contextPath}/">ShopEE</a>
        <p class="pLogoName2">online shop</p></div>
    <div class="divLogin">
        <sec:authorize access="!isAuthenticated()"><br>
            <a href="<c:url value="/login" />">Login</a>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <a href="<c:url value="/logout" />"><span style="font-size: 12pt;">
            Your login:<br/><sec:authentication property="principal.username"/><br/></span>
            Logout</a>
        </sec:authorize>
    </div>
    <div class="divCart"><br><a class="cart" href="#" onclick="openCart()">Cart</a></div>
    <div class="divAd">Advertisement<br/>
        <strong>SALE -75%</strong></div>
</div>

