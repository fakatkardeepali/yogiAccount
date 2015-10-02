<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js" ng-app="app"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="cache-control" content="no-cache"/>
    <title><g:layoutTitle default="Grails"/></title>
    %{--<meta name="viewport" content="width=device-width, initial-scale=1.0">--}%
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
    %{--<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">--}%
    %{--<link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">--}%
    %{--<link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">--}%

    <asset:stylesheet src="application.css"/>
    <asset:javascript src="application.js"/>
    <g:layoutHead/>

</head>

<body ng-controller="AppCtrl" id="app" class="app">

%{--<div id="grailsLogo" role="banner"><a href="http://grails.org"><asset:image src="grails_logo.png" alt="Grails"/></a></div>--}%
<section ng-include src="'../view/header.html'" id="header" class="header-container"
         data-ng-controller="HeaderCtrl" data-ng-class="{ 'header-fixed': admin.fixedHeader,
                                  'bg-white': ['11','12','13','14','15','16','21'].indexOf(admin.skin) >= 0,
                                  'bg-dark': admin.skin === '31',
                                  'bg-primary': ['22','32'].indexOf(admin.skin) >= 0,
                                  'bg-success': ['23','33'].indexOf(admin.skin) >= 0,
                                  'bg-info-alt': ['24','34'].indexOf(admin.skin) >= 0,
                                  'bg-warning': ['25','35'].indexOf(admin.skin) >= 0,
                                  'bg-danger': ['26','36'].indexOf(admin.skin) >= 0}"></section>

<div class="main-container">
    <aside ng-include src="'../view/nav.html'" id="nav-container" class="nav-container"
           data-ng-class="{ 'nav-fixed': admin.fixedSidebar,
                                        'nav-horizontal': admin.menu === 'horizontal',
                                        'nav-vertical': admin.menu === 'vertical',
                                        'bg-white': ['31','32','33','34','35','36'].indexOf(admin.skin) >= 0,
                                        'bg-dark': ['31','32','33','34','35','36'].indexOf(admin.skin) < 0}">

    </aside>

    <div id="content" class="content-container">

        <section data-ng-view class="view-container {{admin.pageTransition.class}}"></section>

        %{--<g:layoutBody/>--}%
    </div>
</div>
%{--============================================== Akshay =======================================================--}%

%{--//place modal to main page to access every where that we want--}%

<script type="text/ng-template" id="accountSettingModal.html">
<section ng-include src="'../view/accountSetting/accountFeature.html'"></section>
</script>
%{--============================================== Akshay =======================================================--}%

%{--//place modal to main page to access every where that we want--}%
<!--// pop up window-->
<script type="text/ng-template" id="interestParameter.html">
<div class="modal-header">
    <h3>Interest Parameter</h3>
</div>

<div class="modal-body">
    <div class="col-md-12 well">
        <div class="panel panel-default">
            <!--<div class="panel-heading" ><strong><span class="glyphicon glyphicon-th"></span>Bill-Wise Details</strong></div>-->
            <table class="table">
                <tr>
                    <th class="col-md-1"><a href="" data-ng-click="addRow()">
                        <span class="glyphicon glyphicon-plus"></span></a>Action
                    </th>
                    <!--<th class="col-md-1 text-center"></th>-->
                    <th class="col-md-1 text-center">Rate(%)<span
                            class="reqColor glyphicon glyphicon-star"></span></th>
                    <th class="col-md-1 text-center">Per</th>
                    <th class="col-md-3 text-center">From</th>
                    <th class="col-md-3 text-center">To</th>
                </tr>
            </table>

            <div data-slim-scroll="" data-scroll-height="150px"
                 style="overflow: hidden; width: auto; height: 150px;">
                <table class="table-bordered">
                    <tr ng-repeat="child in interestPara track by $index">
                        <td class="col-md-1">
                            <!--<a href="" data-ng-click="updateChild($index)"><span class="glyphicon glyphicon-edit"></span></a>-->
                            <a href="" data-ng-click="interestPara.splice($index,1)"><span
                                    class="glyphicon glyphicon-trash"></span></a>
                        </td>
                        <td class="col-md-2"><input type="text" class="form-control input-sm"
                                                    ng-model="child.rate">
                        </td>
                        <td class="col-md-3"><select ng-model="child.rateper"
                                                     class="form-control input-sm"
                                                     ng-options="tr for tr in ratePerList"></select>
                        </td>
                        <td class="col-md-3"><input type="date" class="form-control input-sm"
                                                    ng-model="child.applicableDateFrom"></td>
                        <td class="col-md-3"><input type="date" class="form-control input-sm"
                                                    ng-model="child.applicableDateTo"></td>
                    </tr>
                </table>
            </div>

            <div class="panel-bottom">
                <table class="table">
                    <tr>
                        <td class="col-md-2"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"></td>
                        <td class="col-md-1"><span class="col-md-8 label label-success"></span>
                        </td>
                        <td class="col-md-1"><span
                                class="col-md-8 label label-success"></span></td>

                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal-footer">
    <button class="btn btn-primary" ng-click="close()">OK</button>
    <!--<button class="btn btn-warning" ng-click="cancel()">Cancel</button>-->
</div>
</script>
%{--============================================== Akshay =======================================================--}%

%{--//place modal to main page to access every where that we want--}%
<!--// pop up window-->
<script type="text/ng-template" id="MaintainBillByBill.html">
<div class="modal-header">
    <h3>Maintain Bill Break Up</h3>
</div>

<div class="modal-body">
    <div class="col-md-12">
        <div class="panel panel-default">
            <!--<div class="panel-heading" ><strong><span class="glyphicon glyphicon-th"></span>Bill-Wise Details</strong></div>-->
            <table class="table">
                <tr>
                    <th class="col-md-2"><a href="" data-ng-click="addBillChild()"><span
                            class="glyphicon glyphicon-plus"></span></a> Action
                    </th>
                    <th class="col-md-2 text-center">Date<span
                            class="reqColor glyphicon glyphicon-star"></span></th>
                    <th class="col-md-2 text-center">Bill No<span
                            class="reqColor glyphicon glyphicon-star"></span></th>
                    <th class="col-md-2 text-center">Cr Days</th>
                    <th class="col-md-4 text-center">Amount(Dr/Cr)</th>
                </tr>
            </table>

            <div data-slim-scroll="" data-scroll-height="120px"
                 style="overflow: hidden; width: auto; height: 150px;">
                <table class="table-bordered">
                    <tr ng-repeat="bc in billChildJSON track by $index">
                        <td class="col-md-2">
                            <a href="" data-ng-click="billChildJSON.splice($index,1)"><span
                                    class="glyphicon glyphicon-trash"></span></a>
                        </td>
                        <td class="col-md-2">
                            <input id="typeRef" ng-model="bc.date"
                                   class="form-control input-sm" type="date">
                        </td>
                        <td class="col-md-2">
                            <input id="billNo" type="text" ng-model="bc.billNo" class="form-control input-sm"
                                   ng-model="bc.billNo">
                        </td>
                        <td class="col-md-2">
                            <input id="crDays" type="text" class="form-control input-sm"
                                   ng-model="bc.crDays"
                                   data-ng-change="bc.crDays=isNumber(bc.crDays)">
                        </td>
                        <td class="col-md-2 text-right">
                            <input id="amount" type="text"
                                   data-ng-change="bc.amount=isNumber(bc.amount);calculateAmount()"
                                   class="form-control input-sm" ng-model="bc.amount">
                        </td>
                        <td class="col-md-2">
                            <select id="amountStatus" ng-model="bc.amountStatus"
                                    class="form-control input-sm" data-ng-change="calculateAmount()"
                                    ng-options="tr for tr in amountStatusList"></select>
                        </td>
                    </tr>
                </table>
                <table class="pull-right">
                    <tbody>
                    <tr><td></td>
                        <td ng-bind="tableTotal"></td><td>{{status1}}</td>
                    </tr><tr><td>On Account</td>
                        <td ng-bind="onAccount"></td><td>{{status2}}</td>
                    </tr><tr><td>Total</td>
                        <td ng-bind="openingAmount"></td><td>{{status3}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal-footer">
    <button class="btn btn-line-info" ng-click="closeBill()">OK</button>
    %{--<button class="btn btn-warning" ng-click="cancelBill()">Cancel</button>--}%
</div>
</script>

%{--============================================== Akshay =======================================================--}%

<script type="text/ng-template" id="statutoryModal.html">
<section ng-include src="'../view/accountSetting/statutorySetting.html'"></section>
</script>

%{--=============================================== bhupendra ===========================================================--}%
%{--//for multiple company selection modal popup--}%
<script type="text/ng-template" id="myModalContent.html">
<div class="modal-header">
    <h3>Change Company</h3>
    <h4><label class="reqColor">Note: Not select more than 1 compnay</label></h4>
</div>

<div class="modal-body">
    <ul ng-repeat="item in items" class="list-unstyled">
        <li>
            <!--<label class="ui-checkbox">-->
            <input type="checkbox" name="company" ng-model="item.status"
                   ng-change="countCompany()">
            <b>{{item.name}}</b>
            <!--</label>-->
        </li>
    </ul>
</div>

<div class="modal-footer">
    <button class="btn btn-primary" ng-click="ok()" ng-disabled="buttonDisable">OK</button>
    <!--<button class="btn btn-warning" ng-click="cancel()">Cancel</button>-->
</div>
</script>
%{--<div class="footer" role="contentinfo"></div>--}%
%{--<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>--}%

%{--strat groupSummaryReport--}%
<script type="text/ng-template" id="groupSummaryTemplate.html">
<section class="panel panel-dark" ng-if="parentData.companyName">
    <!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{parentData.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-left" colspan="8">{{parentData.reportName}}</th>
                            <th class="col-xs-2 text-center" style="font-size:small;" colspan="2">From:
                            {{parentData.fromDate}}
                            </th>
                            <th class="col-xs-2 text-center" style="font-size:small;" colspan="2">To:
                            {{parentData.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-center" colspan="8">Group Name:
                            {{parentData.groupName}}
                            </th>
                            <th class="col-xs-4 text-center" colspan="4">Closing Balance</th>
                        </tr>
                        <tr>
                            <th class="col-xs-8" colspan="8">Particulars</th>
                            <th class="col-xs-2 text-right" colspan="2">Credit</th>
                            <th class="col-xs-2 text-right" colspan="2">Debit</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in childData">
                            <td class="col-xs-8 text-left" style="font-size:small;" colspan="8">
                                <span data-ng-click="getInfoGroupAndLedger(child)"><a href="">{{child.name}}</a></span>
                            </td>
                            <td class="col-xs-2 text-right" style="font-size:small;" colspan="2">
                                {{(child.amountStatus=="Cr")?child.amount:0|number:2}}
                            </td>
                            <td class="col-xs-2 text-right" style="font-size:small;" colspan="2">
                                {{(child.amountStatus=="Dr")?child.amount:0|number:2}}
                            </td>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-right" colspan="8">Grand Total</th>
                            <th class="col-xs-2 text-right" colspan="2">
                                {{parentData.totalCrAmount|number:2}}
                            </th>
                            <th class="col-xs-2 text-right" colspan="2">
                                {{parentData.totalDrAmount|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>
                %{--<button type="button" class="btn btn-line-primary"--}%
                %{--data-ng-click="printInvoice()">--}%
                %{--Print--}%
                %{--</button>--}%

                <a ng-href="../groupSummaryReport/groupSummaryReport1?groupObject={{(groupObject)}}&isGroup=true"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
    </div>
</section>
</script>
%{--end groupSummaryReport--}%

%{--strat legerReport--}%
<script type="text/ng-template" id="ledgerReportTemplate.html">
<section class="panel panel-dark" ng-if="reportParent.companyName">
    <!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{reportParent.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" colspan="8">{{reportParent.reportName}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">From:
                            {{reportParent.fromDate}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">To:
                            {{reportParent.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8">Ledger
                            Name: {{reportParent.partyName}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Opening
                            Balance
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.openBalCr|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.openBalDr|number:2}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="1">Date</th>
                            <th class="text-center col-xs-6" style="font-size:small;" colspan="7">
                                Particulars
                            </th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.Type
                            </th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.No
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">Credit</th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">Debit</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in reportChild">
                            <td class="text-center col-xs-2" style="font-size:small;" colspan="1">
                                {{child.date}}
                            </td>
                            <td class="text-left col-xs-6" style="font-size:small;" colspan="7"><a
                                    ng-href="#voucher/create/{{child.id}}">{{child.particulars}}</a></td>
                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vType}}
                            </td>
                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vNo}}
                            </td>
                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.credit|number:2}}

                            </td>
                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.debit|number:2}}
                            </td>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Current
                            Total:
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.crAmount|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.drAmount|number:2}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Closing
                            Balance
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.totalCr|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.totalDr|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>

                <a ng-href="../groupSummaryReport/groupSummaryReport1?groupObject={{groupObject}}&isGroup=''&isGroupVoucher=true"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
</div>
</section>
</script>
%{--end legerReport--}%

%{--strat GroupVoucherReport--}%
<script type="text/ng-template" id="ledgerVoucherReportTemplate.html">
<section class="panel panel-dark" ng-if="reportParent.companyName">
    <!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{reportParent.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" colspan="8">{{reportParent.reportName}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">From:
                            {{reportParent.fromDate}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">To:
                            {{reportParent.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8">Group
                            Name: {{reportParent.partyName}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Opening
                            Balance
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.openBalCr|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.openBalDr|number:2}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="1">Date</th>
                            <th class="text-center col-xs-6" style="font-size:small;" colspan="7">
                                Particulars
                            </th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.Type
                            </th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.No
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">Credit</th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">Debit</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in reportChild">
                            <td class="text-center col-xs-2" style="font-size:small;" colspan="1">
                                {{child.date}}
                            </td>
                            <td class="text-left col-xs-6" style="font-size:small;" colspan="7"><a
                                    ng-href="#voucher/create/{{child.id}}">{{child.particulars}}</a></td>
                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vType}}
                            </td>
                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vNo}}
                            </td>
                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.credit|number:2}}

                            </td>
                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.debit|number:2}}
                            </td>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Current
                            Total:
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.crAmount|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.drAmount|number:2}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Closing
                            Balance
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.totalCr|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.totalDr|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>

                <a ng-href="../groupSummaryReport/groupSummaryReport1?groupObject={{groupObject}}&isGroup={{isGroup}}&isGroupVoucher={{isGroupVoucher}}"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
    </div>
</section>
</script>
%{--end GroupVoucherReport--}%

%{--strat saleRegisterReport--}%
<script type="text/ng-template" id="saleRegisterReportTemplate.html">
<section class="panel panel-dark" ng-if="reportParent.companyName">
    <!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{reportParent.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" colspan="8">{{reportParent.reportName}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">From:
                            {{reportParent.fromDate}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">To:
                            {{reportParent.toDate}}
                            </th>
                        </tr>
                        %{--<tr>--}%
                        %{--<th class="text-center col-xs-8" style="font-size:small;" colspan="8">Group--}%
                        %{--Name: {{reportParent.partyName}}--}%
                        %{--</th>--}%
                        %{--<th class="text-center col-xs-2" style="font-size:small;" colspan="2">Opening--}%
                        %{--Balance--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.openBalCr|number:2}}--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.openBalDr|number:2}}--}%
                        %{--</th>--}%
                        %{--</tr>--}%
                        <tr>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="1">Date</th>
                            <th class="text-center col-xs-6" style="font-size:small;" colspan="7">
                                Particulars
                            </th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.Type
                            </th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.No
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">Credit</th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">Debit</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in reportChild track by child.id">
                            <td class="text-center col-xs-2" style="font-size:small;" colspan="1">
                                {{child.date}}
                            </td>
                            <td class="text-left col-xs-6" style="font-size:small;" colspan="7"><a
                                    ng-href="#voucher/create/{{child.id}}">{{child.particulars}}</a>
                            </td>
                            %{--<td class="text-left col-xs-6" style="font-size:small;" colspan="7">--}%
                            %{--{{child.particulars}}--}%
                            %{--</td>--}%
                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vType}}
                            </td>
                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vNo}}
                            </td>
                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.credit|number:2}}

                            </td>
                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.debit|number:2}}
                            </td>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Current
                            Total:
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.creditTotal|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.debitTotal|number:2}}
                            </th>
                        </tr>
                        %{--<tr>--}%
                        %{--<th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>--}%
                        %{--<th class="text-center col-xs-2" style="font-size:small;" colspan="2">Closing--}%
                        %{--Balance--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.totalCr|number:2}}--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.totalDr|number:2}}--}%
                        %{--</th>--}%
                        %{--</tr>--}%
                    </table>
                </div>
                %{--<button id="delete" type="button" class="btn btn-line-primary"--}%
                %{--data-ng-click="printInvoice()">--}%
                %{--Print--}%
                %{--</button>--}%

                <a ng-href="../journalRegisterReport/voucherReports?groupObject={{groupObject}}"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
    </div>
</section>
</script>
%{--end saleRegisterReport--}%

%{--strat groupOutstandingReport--}%
<script type="text/ng-template" id="groupOutstanding.html">
<section class="panel panel-dark" ng-if="parentData.companyName">
    <!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{parentData.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-left" colspan="8">{{parentData.reportName}}</th>
                            <th class="col-xs-2 text-center" style="font-size:small;" colspan="2">From:
                            {{parentData.fromDate}}
                            </th>
                            <th class="col-xs-2 text-center" style="font-size:small;" colspan="2">To:
                            {{parentData.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-center" colspan="12">Group Name:
                            {{parentData.groupName}}
                            </th>
                            %{--<th class="col-xs-4 text-center" colspan="4">Closing Balance</th>--}%
                        </tr>
                        <tr>
                            <th class="col-xs-8" colspan="8">Particulars</th>
                            <th class="col-xs-2 text-right" colspan="2">Credit</th>
                            <th class="col-xs-2 text-right" colspan="2">Debit</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in childData">
                            <td class="col-xs-8 text-left" style="font-size:small;" colspan="8">
                                <span data-ng-click="getInfoLedger(child)"><a href="">{{child.name}}</a></span>
                            </td>
                            <td class="col-xs-2 text-right" style="font-size:small;" colspan="2">
                                {{(child.amountStatus=="Cr")?child.amount:0|number:2}}
                            </td>
                            <td class="col-xs-2 text-right" style="font-size:small;" colspan="2">
                                {{(child.amountStatus=="Dr")?child.amount:0|number:2}}
                            </td>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-right" colspan="8">Grand Total</th>
                            <th class="col-xs-2 text-right" colspan="2">
                                {{parentData.totalCrAmount|number:2}}
                            </th>
                            <th class="col-xs-2 text-right" colspan="2">
                                {{parentData.totalDrAmount|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>
                %{--<button type="button" class="btn btn-line-primary"--}%
                %{--data-ng-click="printInvoice()">--}%
                %{--Print--}%
                %{--</button>--}%

                <a ng-href="../groupOutstandingReport/groupOutstandingReport?groupObject={{groupObject}}&isGroup={{isGroup}}"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
    </div>
</section>
</script>
%{--end groupOutstandingReport--}%

%{--strat ledgerOutstandingReport--}%
<script type="text/ng-template" id="ledgerOutstanding.html">
<section class="panel panel-dark" ng-if="reportParent.companyName">
    %{--<!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->--}%
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{reportParent.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" colspan="8">{{reportParent.reportName}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">
                                From: {{reportParent.fromDate}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">
                                To:{{reportParent.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-12" style="font-size:small;" colspan="12">
                                Ledger Name: {{reportParent.partyName}}
                            </th>
                            %{--<th class="text-center col-xs-2" style="font-size:small;" colspan="2">--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.openBalCr|number:2}}--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.openBalDr|number:2}}--}%
                        %{--</th>--}%
                        %{--</tr>--}%
                        <tr>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Date</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Ref No.</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.Type</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.No</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Opening Amount</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Pending Amount</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Due On</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Overdue</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in reportChild">
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">{{child.date}}</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">{{child.refNo}}</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1"><a
                                    ng-href="#voucher/create/{{child.id}}">{{child.vType}}</a></th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">{{child.vNo}}</th>
                            <th class="text-right col-xs-2" style="font-size:small;"
                                colspan="2">{{child.amount|number:2}}{{child.status}}</th>
                            <th class="text-right col-xs-2" style="font-size:small;"
                                colspan="2">{{child.remainAmount|number:2}}{{child.status}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">{{child.dueOn}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">{{child.overDue}}</th>
                        </tr>
                        <tr>
                            %{--<th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>--}%
                            %{--<th class="text-center col-xs-2" style="font-size:small;" colspan="2">Current--}%
                            %{--Total:--}%
                            %{--</th>--}%
                            %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{reportParent.creditTotal|number:2}}--}%
                            %{--</th>--}%
                            %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{reportParent.debitTotal|number:2}}--}%
                            %{--</th>--}%
                        </tr>
                        <tr>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan=4"></th>
                            <th class="text-right col-xs-2" style="font-size:small;" colspan="2">
                                {{reportParent.totalOpeningAmount|number:2}}{{reportParent.openingAmountStatus}}
                            </th>
                            <th class="text-right col-xs-2" style="font-size:small;" colspan="2">
                                {{reportParent.totalRemainAmount|number:2}}{{reportParent.remianAmountStatus}}
                            </th>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan=4"></th>
                        </tr>
                    </table>
                </div>
                %{--<button id="delete" type="button" class="btn btn-line-primary"--}%
                %{--data-ng-click="printInvoice()">--}%
                %{--Print--}%
                %{--</button>--}%

                <a ng-href="../ledgerOutstandingReports/ledgerOutstandingReport?groupObject={{obj}}&isGroup={{isGroup}}&isGroupVoucher={{isGroupVoucher}}"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
    </div>
</section>
</script>
%{--end ledgerOutstandingReport--}%

%{--strat payablesOutstandingReport--}%
<script type="text/ng-template" id="payablesOutstanding.html">

<section class="panel panel-dark" ng-if="reportParent.companyName">
    %{--<!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->--}%
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{reportParent.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" colspan="8">{{reportParent.reportName}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">From:
                            {{reportParent.fromDate}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">To:
                            {{reportParent.toDate}}
                            </th>
                        </tr>
                        %{--<tr>--}%
                        %{--<th class="text-center col-xs-8" style="font-size:small;" colspan="8">Group--}%
                        %{--Name: {{reportParent.partyName}}--}%
                        %{--</th>--}%
                        %{--<th class="text-center col-xs-2" style="font-size:small;" colspan="2">Opening--}%
                        %{--Balance--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.openBalCr|number:2}}--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.openBalDr|number:2}}--}%
                        %{--</th>--}%
                        %{--</tr>--}%
                        <tr>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Date</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Ref No.</th>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan="4">Party's Name</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.Type</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.No</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Opening Amount</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Pending Amount</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Due On</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Overdue</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in reportChild">
                            <td class="text-center col-xs-1" style="font-size:small;" colspan="1">
                                {{child.date}}
                            </td>
                            <td class="text-center col-xs-1" style="font-size:small;" colspan="1">
                                {{child.refNo}}
                            </td>
                            <td class="text-left col-xs-4" style="font-size:small;" colspan="4"><a
                                    ng-href="#voucher/create/{{child.id}}">{{child.particulars}}</a>
                            </td>
                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vType}}
                            </td>
                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vNo}}
                            </td>
                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.openingAmount|number:2}}
                            </td>
                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.pendingAmount|number:2}}
                            </td>
                            <td class="text-center col-xs-1" style="font-size:small;" colspan="1">
                                {{child.dueOn}}
                            </td>
                            <td class="text-center col-xs-1" style="font-size:small;" colspan="1">
                                {{child.overDue}}
                            </td>
                        </tr>
                        <tr>
                            %{--<th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>--}%
                            %{--<th class="text-center col-xs-2" style="font-size:small;" colspan="2">Current--}%
                            %{--Total:--}%
                            %{--</th>--}%
                            %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{reportParent.creditTotal|number:2}}--}%
                            %{--</th>--}%
                            %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{reportParent.debitTotal|number:2}}--}%
                            %{--</th>--}%
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Total
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{reportParent.totalPendingAmount|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                %{--{{reportParent.totalDr|number:2}}--}%
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                %{--{{reportParent.totalDr|number:2}}--}%
                            </th>
                        </tr>
                    </table>
                </div>
                %{--<button id="delete" type="button" class="btn btn-line-primary"--}%
                %{--data-ng-click="printInvoice()">--}%
                %{--Print--}%
                %{--</button>--}%

                <a ng-href="../payablesOutstandingReport/payablesOutstandingReport?groupObject={{groupObject}}"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
    </div>
</section>
</script>
%{--end payablesOutstandingReport--}%

%{--strat statisticsReportReport--}%
<script type="text/ng-template" id="statisticsReport.html">
%{--ng-if="reportParent.companyName"--}%
<section class="panel panel-dark">
    %{--<!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->--}%
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{reportParent.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" colspan="8">{{reportParent.reportName}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">From:
                            {{reportParent.fromDate}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">To:
                            {{reportParent.toDate}}
                            </th>
                        </tr>
                        %{--<tr>--}%
                        %{--<th class="text-center col-xs-8" style="font-size:small;" colspan="8">Group--}%
                        %{--Name: {{reportParent.partyName}}--}%
                        %{--</th>--}%
                        %{--<th class="text-center col-xs-2" style="font-size:small;" colspan="2">Opening--}%
                        %{--Balance--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.openBalCr|number:2}}--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.openBalDr|number:2}}--}%
                        %{--</th>--}%
                        %{--</tr>--}%
                        <tr>
                            <th class="text-center col-xs-5" style="font-size:small;" colspan="5">Types of Vouchers</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Count</th>
                            <th class="text-center col-xs-5" style="font-size:small;" colspan="5">Types of Accounts</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Count</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in reportChild">
                            <td class="text-center col-xs-5" style="font-size:small;" colspan="5">
                                {{child.voucherType}}
                            </td>
                            <td class="text-center col-xs-1" style="font-size:small;" colspan="1">
                                {{child.count}}
                            </td>
                            <td class="text-center col-xs-5" style="font-size:small;" colspan="5">
                                {{child.accountType}}
                            </td>
                            <td class="text-center col-xs-1" style="font-size:small;" colspan="1">
                                {{child.count1}}
                            </td>
                            %{--<td class="text-left col-xs-4" style="font-size:small;" colspan="4"><a--}%
                            %{--ng-href="#voucher/create/{{child.id}}">{{child.particulars}}</a>--}%
                            %{--</td>--}%
                            %{--<td class="text-left col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{child.vType}}--}%
                            %{--</td>--}%
                            %{--<td class="text-left col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{child.vNo}}--}%
                            %{--</td>--}%
                            %{--<td class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{child.openingAmount|number:2}}--}%
                            %{--</td>--}%
                            %{--<td class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{child.pendingAmount|number:2}}--}%
                            %{--</td>--}%
                            %{--<td class="text-center col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{child.dueOn}}--}%
                            %{--</td>--}%
                            %{--<td class="text-center col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{child.overDue}}--}%
                            %{--</td>--}%
                        </tr>
                        <tr>
                            %{--<th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>--}%
                            %{--<th class="text-center col-xs-2" style="font-size:small;" colspan="2">Current--}%
                            %{--Total:--}%
                            %{--</th>--}%
                            %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{reportParent.creditTotal|number:2}}--}%
                            %{--</th>--}%
                            %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--{{reportParent.debitTotal|number:2}}--}%
                            %{--</th>--}%
                        </tr>
                        <tr>
                            <th class="text-center col-xs-5" style="font-size:small;" colspan="5">Total</th>
                            <th class="text-center col-xs-1" style="font-size:small;"
                                colspan="1">{{reportParent.totalCount}}
                            </th>
                            <th class="text-center col-xs-5" style="font-size:small;" colspan="5">
                            </th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">
                            </th>
                        </tr>
                    </table>
                </div>
                %{--<button id="delete" type="button" class="btn btn-line-primary"--}%
                %{--data-ng-click="printInvoice()">--}%
                %{--Print--}%
                %{--</button>--}%

                <a ng-href="../statisticsReport/statisticsReport"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
</div>
</section>
</script>
%{--end statisticsReportReport--}%

%{--strat cashBankSummaryReport--}%
<script type="text/ng-template" id="listOfAccounts.html">
%{--ng-if="reportParent.companyName"--}%
<section class="panel panel-dark">
    %{--<!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->--}%
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{reportParent.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" colspan="8">{{reportParent.reportName}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">From:
                            {{reportParent.fromDate}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">To:
                            {{reportParent.toDate}}
                            </th>
                        </tr>
                        %{--<tr>--}%
                        %{--<th class="text-center col-xs-8" style="font-size:small;" colspan="8">Group--}%
                        %{--Name: {{reportParent.partyName}}--}%
                        %{--</th>--}%
                        %{--<th class="text-center col-xs-2" style="font-size:small;" colspan="2">Opening--}%
                        %{--Balance--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.openBalCr|number:2}}--}%
                        %{--</th>--}%
                        %{--<th class="text-right col-xs-1" style="font-size:small;" colspan="1">--}%
                        %{--{{reportParent.openBalDr|number:2}}--}%
                        %{--</th>--}%
                        %{--</tr>--}%

                        <!--ng-reapet-->
                        <tr ng-repeat="child in reportChild">
                            <td class="text-left col-xs-12" style="font-size:small;" colspan="12">
                                {{child.name}}
                            </td>
                        </tr>
                        <tr>
                            %{--<th class="text-center col-xs-5" style="font-size:small;" colspan="5">Total</th>--}%
                            %{--<th class="text-center col-xs-1" style="font-size:small;"--}%
                            %{--colspan="1">{{reportParent.totalCount}}--}%
                            %{--</th>--}%
                            %{--<th class="text-center col-xs-5" style="font-size:small;" colspan="5">--}%
                            %{--</th>--}%
                            %{--<th class="text-center col-xs-1" style="font-size:small;" colspan="1">--}%
                            %{--</th>--}%
                        </tr>
                    </table>
                </div>
                %{--<button id="delete" type="button" class="btn btn-line-primary"--}%
                %{--data-ng-click="printInvoice()">--}%
                %{--Print--}%
                %{--</button>--}%

                <a ng-href="../statisticsReport/statisticsReport"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
</div>
</section>
</script>

<script type="text/ng-template" id="cashBankSummaryTemplate.html">
<section class="panel panel-dark" ng-if="parentData.companyName">
    <!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{parentData.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-left" colspan="8">{{parentData.reportName}}</th>
                            <th class="col-xs-2 text-center" style="font-size:small;" colspan="2">From:
                            {{parentData.fromDate}}
                            </th>
                            <th class="col-xs-2 text-center" style="font-size:small;" colspan="2">To:
                            {{parentData.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-center" colspan="8">
                            </th>
                            <th class="col-xs-4 text-center" colspan="4">Closing Balance</th>
                        </tr>
                        <tr>
                            <th class="col-xs-8" colspan="8">Particulars</th>
                            <th class="col-xs-2 text-right" colspan="2">Credit</th>
                            <th class="col-xs-2 text-right" colspan="2">Debit</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in childData">
                            <td class="col-xs-8 text-left" style="font-size:small;" colspan="8">
                                <span data-ng-click="getInfoGroupAndLedger(child)"><a href="">{{child.name}}</a></span>
                            </td>
                            <td class="col-xs-2 text-right" style="font-size:small;" colspan="2">
                                {{(child.amountStatus=="Cr")?child.amount:0|number:2}}
                            </td>
                            <td class="col-xs-2 text-right" style="font-size:small;" colspan="2">
                                {{(child.amountStatus=="Dr")?child.amount:0|number:2}}
                            </td>
                    </tr>
                        <tr>
                            <th class="col-xs-8 text-right" colspan="8">Grand Total</th>
                            <th class="col-xs-2 text-right" colspan="2">
                                {{parentData.totalCrAmount|number:2}}
                            </th>
                            <th class="col-xs-2 text-right" colspan="2">
                                {{parentData.totalDrAmount|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>
                %{--<button type="button" class="btn btn-line-primary"--}%
                %{--data-ng-click="printInvoice()">--}%
                %{--Print--}%
                %{--</button>--}%

                <a ng-href="../cashBankSummaryReport/cashBankSummaryReport?groupObject={{groupObject}}&isGroup=true&array={{array}}"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
    </div>
</section>
</script>
%{--end cashBankSummaryReport--}%

%{--strat cashBankLedgerSummaryReport--}%
<script type="text/ng-template" id="cashBankLedgerSummaryTemplate.html">
<section class="panel panel-dark" ng-if="parentData.companyName">
    <!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{parentData.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" colspan="8">{{parentData.reportName}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">From:
                            {{parentData.fromDate}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">To:
                            {{parentData.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8">Group
                            Name: {{parentData.partyName}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Opening
                            Balance
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{parentData.openBalCr|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{parentData.openBalDr|number:2}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="1">Date</th>
                            <th class="text-center col-xs-6" style="font-size:small;" colspan="7">
                                Particulars
                            </th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.Type
                            </th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch.No
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">Credit</th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">Debit</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in childData">
                            <td class="text-center col-xs-2" style="font-size:small;" colspan="1">
                                {{child.date}}
                            </td>
                            <td class="text-left col-xs-6" style="font-size:small;" colspan="7"><a
                                    ng-href="#voucher/create/{{child.id}}">{{child.particulars}}</a></td>
                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vType}}
                            </td>
                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vNo}}
                            </td>
                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.credit|number:2}}

                            </td>
                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.debit|number:2}}
                            </td>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Current
                            Total:
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{parentData.crAmount|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{parentData.drAmount|number:2}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-8" style="font-size:small;" colspan="8"></th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">Closing
                            Balance
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{parentData.totalCr|number:2}}
                            </th>
                            <th class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{parentData.totalDr|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>

                <a ng-href="../cashBankSummaryReport/cashBankSummaryReport?groupObject={{groupObject}}&isGroup=''&isGroupVoucher=true"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
    </div>
</section>
</script>
%{--end cashBankLedgerSummaryReport--}%

%{--strat profitAndLossAcReport--}%
<script type="text/ng-template" id="profitAndLossAcTemplate.html">
<section class="panel panel-dark" ng-if="parentData.companyName">
    <!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-xs-6">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{parentData.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-4" colspan="4">{{parentData.reportName}}</th>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan="4">From:
                            {{parentData.fromDate}}
                            </th>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan="4">To:
                            {{parentData.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Particulars
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">Opening
                            Balance
                            </th>
                        </tr>
                        <tr ng-repeat="child in purchaseChild">
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                <span data-ng-click="getGroupAndLedger(child)"><a href="">{{child.name}}</a></span>
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{child.amount|number:2}}
                            </th>
                        </tr>
                        <tr ng-if="parentData.totalDrAmountStauts">
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                {{parentData.totalDrAmountStauts}}
                            </th>
                            <th class="text-right col-xs-4 greenColor" style="font-size:small;" colspan="4">
                                {{parentData.totalDrAmount|number:2}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Net Purcahse
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{(parentData.totalPurchase+parentData.totalDrAmount)|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="col-xs-6">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{parentData.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-4" colspan="4">{{parentData.reportName1}}</th>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan="4">From:
                            {{parentData.fromDate}}
                            </th>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan="4">To:
                            {{parentData.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Particulars
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">Opening
                            Balance
                            </th>
                        </tr>
                        <tr ng-repeat="child in saleChild">
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                <span data-ng-click="getGroupAndLedger(child)"><a href="">{{child.name}}</a></span>
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{child.amount|number:2}}
                            </th>
                        </tr>
                        <tr ng-if="parentData.totalCrAmountStatus">
                            <th class="text-left col-xs-8 reqColor" style="font-size:small;" colspan="8">
                                {{parentData.totalCrAmountStatus}}
                            </th>
                            <th class="text-right col-xs-4 reqColor" style="font-size:small;" colspan="4">
                                {{parentData.totalCrAmount|number:2}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Net Sale
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{(parentData.totalSale+parentData.totalCrAmount)|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>
            </div>
            <a ng-href="../profitAndLossAcReport/profitAndLossAcReport?groupObject={{groupObject}}&isGroup=true&array={{array}}"
               class="btn btn-line-primary">Print</a>
        </div>
    </div>
</section>
</script>
%{--end profitAndLossAcReport--}%

%{--strat balanceSheetReport--}%
<script type="text/ng-template" id="dayBook.html">
%{--ng-if="reportParent.companyName"--}%
<section class="panel panel-dark">
    %{--<!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->--}%
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{reportParent.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" colspan="8">{{reportParent.reportName}}</th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">From:
                            {{reportParent.fromDate}}
                            </th>
                            <th class="text-center col-xs-2" style="font-size:small;" colspan="2">To:
                            {{reportParent.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Date</th>
                            <th class="text-center col-xs-7" style="font-size:small;" colspan="7">Particulars</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch. Type</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Vch. No.</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Debit Amount</th>
                            <th class="text-center col-xs-1" style="font-size:small;" colspan="1">Credit Amount</th>
                        </tr>

                        <!--ng-reapet-->
                        <tr ng-repeat="child in reportChild">
                            <td class="text-center col-xs-1" style="font-size:small;" colspan="1">
                                {{child.date}}
                            </td>

                            <td class="text-left col-xs-7" style="font-size:small;" colspan="7">
                                <a ng-href="#voucher/create/{{child.id}}">{{child.particulars}}</a>
                            </td>

                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vType}}
                            </td>

                            <td class="text-left col-xs-1" style="font-size:small;" colspan="1">
                                {{child.vNo}}
                            </td>

                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.debitAmount|number:2}}
                            </td>

                            <td class="text-right col-xs-1" style="font-size:small;" colspan="1">
                                {{child.creditAmount|number:2}}
                            </td>
                        </tr>
                    </table>
                </div>
                %{--<button id="delete" type="button" class="btn btn-line-primary"--}%
                %{--data-ng-click="printInvoice()">--}%
                %{--Print--}%
                %{--</button>--}%

                <a ng-href="../dayBookReport/dayBookReport?groupObject={{groupObject}}"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
    </div>
</section>
</script>

<script type="text/ng-template" id="balanceSheetTemplate.html">
<section class="panel panel-dark" ng-if="parentData.companyName">
    <!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-xs-6">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{parentData.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-4" colspan="4">{{parentData.reportName1}}</th>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan="4">From:
                            {{parentData.fromDate}}
                            </th>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan="4">To:
                            {{parentData.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Particulars
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">Opening
                            Balance
                            </th>
                        </tr>
                        <tr ng-repeat="child in purchaseChild">
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                <span data-ng-click="getGroupAndLedger(child)"><a href="">{{child.name}}</a></span>
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{child.amount|number:2}}
                            </th>
                        </tr>
                        <tr ng-if="parentData.openingDrAmount">
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Difference in opening Balances
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{parentData.openingDrAmount|number:2}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Total
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{parentData.totalDrAmount+parentData.openingDrAmount|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="col-xs-6">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{parentData.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-4" colspan="4">{{parentData.reportName2}}</th>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan="4">From:
                            {{parentData.fromDate}}
                            </th>
                            <th class="text-center col-xs-4" style="font-size:small;" colspan="4">To:
                            {{parentData.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Particulars
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">Opening
                            Balance
                            </th>
                        </tr>
                        <tr ng-repeat="child in saleChild">
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                <span data-ng-click="getGroupAndLedger(child)"><a href="">{{child.name}}</a></span>
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{child.amount|number:2}}
                            </th>
                        </tr>
                        <tr ng-repeat="child in profitLossChild">
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                <span data-ng-click="getProfitAndLossReport()"><a href="">{{child.name}}</a></span>
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{child.amount|number:2}}
                            </th>
                        </tr>
                        <tr ng-if="parentData.openingCrAmount">
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Difference in opening Balances
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{parentData.openingCrAmount|number:2}}
                            </th>
                        </tr>
                        <tr>
                            <th class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Total
                            </th>
                            <th class="text-right col-xs-4" style="font-size:small;" colspan="4">
                                {{parentData.totalCrAmount+parentData.openingCrAmount|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>
            </div>
            <a ng-href="../balanceSheetReport/generateBalanceSheetReport?groupObject={{groupObject}}&array={{array}}"
               class="btn btn-line-primary">Print</a>
        </div>
    </div>
</section>
</script>
%{--end balanceSheetReport--}%

%{--strat trailBalanceReport--}%
<script type="text/ng-template" id="trailBalanceTemplate.html">
<section class="panel panel-dark" ng-if="parentData.companyName">
    <!--<div class="panel-heading"><span class="glyphicon"></span>Group Summary</div>-->
    <div class="panel-body">
        <div class="form-horizontal">
            <div class="col-md-12">
                <div class="invoice-inner" id="summary">
                    <table class="table table-bordered table-striped ">
                        <tr>
                            <th class="size-h3 text-center col-xs-12" colspan="12">
                                {{parentData.companyName}}
                            </th>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-left" colspan="8">{{parentData.reportName}}</th>
                            <th class="col-xs-2 text-center" style="font-size:small;" colspan="2">From:
                            {{parentData.fromDate}}
                            </th>
                            <th class="col-xs-2 text-center" style="font-size:small;" colspan="2">To:
                            {{parentData.toDate}}
                            </th>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-center" colspan="8">
                            </th>
                            <th class="col-xs-4 text-center" colspan="4">Closing Balance</th>
                        </tr>
                        <tr>
                            <th class="col-xs-8" colspan="8">Particulars</th>
                            <th class="col-xs-2 text-right" colspan="2">Credit</th>
                            <th class="col-xs-2 text-right" colspan="2">Debit</th>
                        </tr>
                        <!--ng-reapet-->
                        <tr ng-repeat="child in childData">
                            <td class="col-xs-8 text-left" style="font-size:small;" colspan="8">
                                <span data-ng-click="getInfoGroupAndLedger(child)"><a href="">{{child.name}}</a></span>
                            </td>
                            <td class="col-xs-2 text-right" style="font-size:small;" colspan="2">
                                {{(child.amountStatus=="Cr")?child.amount:0|number:2}}
                            </td>
                            <td class="col-xs-2 text-right" style="font-size:small;" colspan="2">
                                {{(child.amountStatus=="Dr")?child.amount:0|number:2}}
                            </td>
                        </tr>
                        <tr>
                            <td class="text-left col-xs-8" style="font-size:small;" colspan="8">
                                Difference in Opening Balance
                            </td>
                            <td class="text-right col-xs-2" style="font-size:small;" colspan="2">
                                {{((parentData.totalDrAmount > parentData.totalCrAmount)?(parentData.totalDrAmount-parentData.totalCrAmount):0)|number:2}}
                            </td>
                            <td class="text-right col-xs-2" style="font-size:small;" colspan="2">
                                {{((parentData.totalDrAmount < parentData.totalCrAmount)?(parentData.totalCrAmount-parentData.totalDrAmount):0)|number:2}}
                            </td>
                        </tr>
                        <tr>
                            <th class="col-xs-8 text-right" colspan="8">Grand Total</th>
                            <th class="col-xs-2 text-right" colspan="2">
                                {{(parentData.totalCrAmount>parentData.totalDrAmount)?parentData.totalCrAmount:parentData.totalDrAmount|number:2}}
                            </th>
                            <th class="col-xs-2 text-right" colspan="2">
                                {{(parentData.totalCrAmount>parentData.totalDrAmount)?parentData.totalCrAmount:parentData.totalDrAmount|number:2}}
                            </th>
                        </tr>
                    </table>
                </div>
                %{--<button type="button" class="btn btn-line-primary"--}%
                %{--data-ng-click="printInvoice()">--}%
                %{--Print--}%
                %{--</button>--}%

                <a ng-href="../trailBalanceReport/generateTrailBalanceReport?groupObject={{groupObject}}&array={{array}}&isGroup={{isGroup}}"
                   class="btn btn-line-primary">Print</a>
            </div>
        </div>
    </div>
</section>
</script>
%{--end trailBalanceReport--}%
</body>
</html>
