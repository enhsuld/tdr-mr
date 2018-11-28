angular
    .module('altairApp')
    .controller("listController",['$scope','$state','$stateParams','user_data', 'lic_type','rep_type','mineralData','p_deposit','min_group','divisions','$filter','mainService',
        function ($scope,$state, $stateParams, user_data, lic_type, rep_type, mineralData, p_deposit, min_group, divisions, $filter, mainService) {
            $scope.divisions = [{text: "Уул уурхайн үйлдвэрлэл, технологийн хэлтэс",value: 1}, {text: "Нүүрсний судалгааны хэлтэс", value: 2}, {text: "Геологи, хайгуулын хэлтэс", value: 3}];

            $scope.repsteps = [[
                {text: "Хүлээн авах хэсэг", value: 1, tab: 2},
                {text: "Нөөцийн хэсэг",value: 2, tab: 3},
                {text: "Технологийн хэсэг", value: 3, tab: 4},
                {text: "Бүтээгдэхүүн борлуулалтын хэсэг",value: 4, tab: 5},
                {text: "Эдийн засгийн хэсэг", value: 5, tab: 6},
                {text: "Эцсийн шийдвэр", value: 6, tab: 7}
            ],[
                {text: "Хүлээн авах хэсэг", value: 1, tab: 2},
                {text: "Нөөцийн хэсэг",value: 2, tab: 3},
                {text: "Технологийн хэсэг", value: 3, tab: 4},
                {text: "Бүтээгдэхүүн борлуулалтын хэсэг",value: 4, tab: 5},
                {text: "Эдийн засгийн хэсэг", value: 5, tab: 6},
                {text: "Эцсийн шийдвэр", value: 6, tab: 7}
            ],[
                {text: "Хүлээн авах хэсэг", value: 8, tab: 2},
                {text: "Геологийн хэсэг",value: 9, tab: 3},
                {text: "Зардлын доод хэмжээ", value: 10, tab: 4}
            ],[
                {text: "Бичвэр мэдээлэл", value: 1, tab: 2},
                {text: "Хавсаргах мэдээлэл",value: 8, tab: 3}
            ]];

            $scope.repstatuses = [{text: "Хадгалсан", value: 0, class:"uk-badge-primary"}, {text: "Засварт буцаасан",value: 2, class: "uk-badge-danger"}, {text: "Илгээсэн", value: 7, class: "uk-badge-warning"},{text: "Баталгаажсан", value: 1, class: "uk-badge-success"},{text: "Татгалзсан", value: 3, class:"uk-badge-danger"}];

            $scope.getAnStatus = function(item){
                return ($filter('filter')($scope.repstatuses, { value: item.repstatusid }, true).length > 0) ? $filter('filter')($scope.repstatuses, { value: item.repstatusid }, true)[0].text : "";
            }

            $scope.getReportType = function(typeid){
                return ($filter('filter')(rep_type, { value: typeid }, true).length > 0) ? $filter('filter')(rep_type, { value: typeid }, true)[0].text : "";
            }

            $scope.getAnStatusClass = function(item){
                return ($filter('filter')($scope.repstatuses, { value: item.repstatusid }, true).length > 0) ? $filter('filter')($scope.repstatuses, { value: item.repstatusid }, true)[0].class : "";
            }

            $scope.getAnButton = function(item){
                return (item.repstatusid == 7) ? ((user_data.step == item.repstepid) ? "Хянах" : "Харах") : "Харах";
            }

            $scope.openNote = function($event,$parentIndex,$index,note) {
                $event.preventDefault();
                $scope.noteActive = true;

                $('.notes_list').children('li').removeClass('md-list-item-active');

                $($event.currentTarget).parent('li').addClass('md-list-item-active');
                var count=0;
                angular.forEach($scope.anObj[0].notes, function(value, key){
                    if(value.inptype == note.inptype){
                        count++;
                    }
                });
                var json=0;
                for (var i = 0; i < $scope.anObj[0].notes.length; i++) {
                    if($scope.anObj[0].notes[i].inptype==note.inptype){
                        if ($scope.anObj[0].notes[i].decision == 0) {
                            json=json+1;
                        }
                    }
                }

                $scope.note_form = {
                    parentIndex: $parentIndex,
                    index: $index,
                    id: note.id,
                    decision:note.decision,
                    title: note.title,
                    content: note.content,
                    comm:note.comment,
                    images: note.images,
                    issaved:note.issaved,
                    planid:$stateParams.param,
                    atfile:note.file,
                    tabid:note.inptype,
                    ccount:json,
                    notesize:count
                };
                console.log($scope.note_form);
                angular.element($window).resize();
            };

            $scope.showDetail = function(item){
                $scope.sgt = null;
                $scope.anObj = null;
                $scope.annObj = null;
                mainService.withdomain('get','/user/service/sgt/'+item.id).then(function(data){
                    $scope.sgt = data[0];
                    mainService.withdomain('get','/user/service/note/imp/'+item.id).then(function(data){
                        $scope.anObj = data;
                        mainService.withdomain('get','/user/service/rs/com.peace.users.model.mram.LnkReqAnn/reqid/'+item.reqid).then(function(data){
                            $scope.config = data;
                            mainService.withdomain('get','/user/service/detail').then(function(data){
                                $scope.org_data = data;
                                UIkit.modal("#modal_detail").show();
                            });
                        });
                    });
                });
            }

            if ($stateParams.view != null){
                switch($stateParams.view) {
                    case "all":
                        var dataSource = new kendo.data.DataSource({
                            transport: {
                                read: {
                                    url: "/user/angular/AnnualRegistrationColor",
                                    contentType: "application/json; charset=UTF-8",
                                    type: "POST",
                                    data: function () {
                                        if (user_data.divisionid != undefined){
                                            if (user_data.divisionid == 1) {
                                                return {"custom": "where repstatusid != 0 and minid!= 5 and ((divisionid = 1) or (DIVISIONID = 3 and LICENSEXB like '%MV-%' and xtype != 0))"};
                                            }
                                            else if (user_data.divisionid == 2) {
                                                return {"custom": "where repstatusid != 0 and minid= 5 and ((divisionid = 2) or (DIVISIONID = 3 and LICENSEXB like '%MV-%' and xtype != 0))"};
                                            }
                                            else {
                                                return {"custom": "where repstatusid != 0 and divisionid = " + user_data.divisionid};
                                            }
                                        }
                                        else{
                                            return {"custom": "where repstatusid != 0"};
                                        }
                                    },
                                },
                                parameterMap: function (options) {
                                    return JSON.stringify(options);
                                }
                            },
                            schema: {
                                data: "data",
                                total: "total",
                                model: {
                                    id: "id",
                                    fields: {
                                        id: {editable: false, nullable: true},
                                        istodotgol: {type: "boolean"}
                                    }
                                }
                            },
                            pageSize: 5,
                            serverPaging: true,
                            serverFiltering: true,
                            serverSorting: true,
                            filter: []
                        });

                        var columnsArray = [
                            {field: "lpName", title: "Байгууллагын нэр", width: "200px"},
                            {field: "lpReg", title: "Байгууллагын РД", hidden: true, width: "200px"},
                            {field: "licenseXB", title: "Лицензийн дугаар", width: "200px"},
                            {field: "divisionid", title: "Хэлтэс", width: "200px", values: divisions},
                            {field: "lictype",width: "200px",title: "Лицензийн төрөл",values: lic_type},
                            {field: "reporttype", title: "Төрөл", values: rep_type, width: "200px"},
                            {field: "xtype",title: "X эсэх",template: "# if(xtype != 0) { # Энгийн #} else {# Х #}#",width: "200px"},
                            {field: "reportyear", title: "Он", width: "200px"},
                            {field: "groupid", title: "АМ-ын ангилал", values: min_group, width: "200px"},
                            {field: "minid", title: "АМ-ын нэр", values: mineralData, width: "200px"},
                            {field: "depositid", title: "АМ-ын төрөл", values: p_deposit, width: "200px"},
                            {field: "repstatusid",values: $scope.repstatuses,template: kendo.template($("#status").html()),title: "Төлөв",width: "200px"},
                            {field: "istodotgol",title: "Тодотгосон эсэх",template: "# if(istodotgol == 0) { # Үгүй #} else {# Тийм #}#",width: "200px"},
                            {field: "repstepid", title: "Үе шат", values: $scope.repsteps, width: "200px"},
                            {field: "rejectstep", title: "Буцаасан шат", values: $scope.repsteps, width: "200px"},
                            {field: "submissiondate", title: "Илгээсэн огноо", width: "200px"},
                            {title: "Үйлдэл", template: kendo.template($("#main").html()), width: "120px", locked: true}
                        ];
                        break;
                }
            }

            $scope.listGrid = {
                dataSource: dataSource,
                columnMenu:true,

                filterable: {
                    mode:"row"
                },
                sortable: {
                    mode: "multiple",
                    allowUnsort: true
                },
                scrollable: true,
                pageable: {
                    refresh:true,
                    buttonCount: 10
                },
                /*height:function(){
                    return $( window ).height() * 0.75;
                },*/
                columns: columnsArray
            };
        }]
    );
