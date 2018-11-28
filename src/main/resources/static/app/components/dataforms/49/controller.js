angular
  .module('altairApp')
  .controller("dataFormsController49", ['$scope', 'mainService', '$stateParams', function($scope, mainService,$stateParams) {

    /*mainService.withdomain("post", "/getPlanListByForm/181").then(function(data) {
      console.log(data);
    });*/
	  
	/*var types = [{value:"TSOONOG", text:"1. Цооног"},{value:"SUVAG", text:"2. Суваг"},{value:"SHURF", text:"3. Шурф"}];*/

    $scope.proleGrid1 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataExcelGeorep3",
            contentType: "application/json; charset=UTF-8",
            type: "POST",
            data:function(){
            	if ($stateParams.param != null && $stateParams.param != undefined && $stateParams.param > 0){
            		return {"custom":" where planid = " + $stateParams.param + " and type = 'TSOONOG'"};
            	}
            },
          },
          parameterMap: function(options) {
            return JSON.stringify(options);
          }
        },
        schema: {
          data: "data",
          total: "total",
          model: {
            id: "id",
          }
        },
        pageSize: 1000,
        serverPaging: true,
        serverFiltering: true,
        scrollable: true,
        serverSorting: true,
        sort: [{
            field: "id",
            dir: "asc"
          },
          {
          field: "ordernumber",
          dir: "asc"
        }]
      },
      filterable: false,
      sortable: true,
      resizable: true,

      pageable: {
        refresh: true,
        pageSizes: true,
        buttonCount: 5
      },
      columns:[
 		{field:"data1", title: "Д/д"},
   		{field:"data2", title: "Цооногийн дугаар"},
   		{title:"Байршил /Солбилцол/",columns:[
   				{title:"Уртраг",columns:[{title:"Град", field:"data3"},{title:"Мин", field:"data4"},{title:"Сек", field:"data5"}]},
   				{title:"Өргөрөг",columns:[{title:"Град", field:"data6"},{title:"Мин", field:"data7"},{title:"Сек", field:"data8"}]}
   			],
   		},
   		{field:"data9", title: "Цооногийн гүн,м"},
   		{field:"data10", title: "Керний гарц хувь"},
   		{field:"data12", title: "Авсан дээж сорьцын төрөл, тоо"},
   		{field:"data13", title: "Эрлийн хэсэг, илрэлийн нэр"}
   	],
      editable: false
    };
    
    $scope.proleGrid2 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataExcelGeorep3",
            contentType: "application/json; charset=UTF-8",
            type: "POST",
            data:function(){
            	if ($stateParams.param != null && $stateParams.param != undefined && $stateParams.param > 0){
            		return {"custom":" where planid = " + $stateParams.param + " and type = 'SUVAG'"};
            	}
            },
          },
          parameterMap: function(options) {
            return JSON.stringify(options);
          }
        },
        schema: {
          data: "data",
          total: "total",
          model: {
            id: "id",
          }
        },
        pageSize: 1000,
        serverPaging: true,
        serverFiltering: true,
        scrollable: true,
        serverSorting: true,
        sort: [{
            field: "id",
            dir: "asc"
          },
          {
          field: "ordernumber",
          dir: "asc"
        }]
      },
      filterable: false,
      sortable: true,
      resizable: true,

      pageable: {
        refresh: true,
        pageSizes: true,
        buttonCount: 5
      },
      columns:[
 		{field:"data1", title: "Д/д"},
   		{field:"data2", title: "Сувгийн дугаар"},
   		{title:"Байршил /Солбилцол/",columns:[
   				{title:"Уртраг",columns:[{title:"Град", field:"data3"},{title:"Мин", field:"data4"},{title:"Сек", field:"data5"}]},
   				{title:"Өргөрөг",columns:[{title:"Град", field:"data6"},{title:"Мин", field:"data7"},{title:"Сек", field:"data8"}]}
   			],
   		},
   		{field:"data9", title: "Сувгийн эзэлхүүн, м3"},
   		{title: "Хэмжээ, м", columns:[{title:"Урт", field:"data10"},{title:"Гүн", field:"data11"}]},
   		{field:"data12", title: "Авсан дээж сорьцын төрөл, тоо"},
   		{field:"data13", title: "Эрлийн хэсэг, илрэлийн нэр"}
   	],
      editable: false
    };
    
    $scope.proleGrid3 = {
      dataSource: {

        transport: {
          read: {
            url: "/user/angular/DataExcelGeorep3",
            contentType: "application/json; charset=UTF-8",
            type: "POST",
            data:function(){
            	if ($stateParams.param != null && $stateParams.param != undefined && $stateParams.param > 0){
            		return {"custom":" where planid = " + $stateParams.param + " and type = 'SHURF'"};
            	}
            },
          },
          parameterMap: function(options) {
            return JSON.stringify(options);
          }
        },
        schema: {
          data: "data",
          total: "total",
          model: {
            id: "id",
          }
        },
        pageSize: 1000,
        serverPaging: true,
        serverFiltering: true,
        scrollable: true,
        serverSorting: true,
        sort: [{
            field: "id",
            dir: "asc"
          },
          {
          field: "ordernumber",
          dir: "asc"
        }]
      },
      filterable: false,
      sortable: true,
      resizable: true,

      pageable: {
        refresh: true,
        pageSizes: true,
        buttonCount: 5
      },
      columns:[
 		{field:"data1", title: "Д/д"},
   		{field:"data2", title: "Шурфын дугаар"},
   		{title:"Байршил /Солбилцол/",columns:[
   				{title:"Уртраг",columns:[{title:"Град", field:"data3"},{title:"Мин", field:"data4"},{title:"Сек", field:"data5"}]},
   				{title:"Өргөрөг",columns:[{title:"Град", field:"data6"},{title:"Мин", field:"data7"},{title:"Сек", field:"data8"}]}
   			],
   		},
   		{field:"data9", title: "Шурфын гүн, м"},
   		{field:"data10", title: "Хэмжээ, м"},
   		{field:"data12", title: "Авсан дээж сорьцын төрөл, тоо"},
   		{field:"data13", title: "Эрлийн хэсэг, илрэлийн нэр"}
   	],
      editable: false
    };

    if ($stateParams.param == null || $stateParams.param == undefined){
    	$scope.proleGrid1.toolbar = ["excel", "pdf"];
    	$scope.proleGrid1.excel = {fileName: "Report.xlsx",allPages: true};
    	$scope.proleGrid1.dataSource.group = {field: "annualRegistration.lpName",dir: "desc"}
    }

  }]);