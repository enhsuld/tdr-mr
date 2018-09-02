angular
    .module('altairApp')
    .controller("weekstatusCtrl", [
        '$scope',
        '$rootScope',
        '$state',
        'utils',
        'mainService',
        'sweet',
        'lic_type',
        'app_status',
        'rep_type',
        'user_data',
        '$filter',
        'weeks',
        function ($scope, $rootScope, $state, utils, mainService, sweet, lic_type, app_status, rep_type, user_data,$filter,weeks) {
            $scope.newWeekReportList = [];
            $scope.domain = "com.peace.users.model.mram.SubLicenses";
            $scope.rrid = 0;
            $scope.newreport = {};

            var modal = UIkit.modal(".uk-modal");

            $scope.plan = function (id) {
                $state.go('restricted.pages.planForm', {param: 0}, {reload: true});
            };

            $scope.selectize_week_options = weeks;

          /*  for(var i=1;i<=53;i++){
                $scope.selectize_week_options.push(i.toString());
            }
*/
            $scope.selectize_week_config = {
                plugins: {
                    'tooltip': ''
                },
                create: false,
                maxItems: 1,
                placeholder: 'Долоо хоногийн дугаарыг сонгох...'
            };

            $scope.initConfig = function () {
                mainService.withdata("POST","/user/angular/AnnualRegistrationCom",{"custom":"where lpReg='"+user_data.lpreg+"' and repstatusid=1 and reporttype=3 and divisionid!=3","take":0,"skip":0,"page":1,"pageSize":0}).then(function (data) {
                    $scope.newWeekReportList = data.data;
                    var weekmodal = UIkit.modal("#modal_newweekreport");
                    weekmodal.show();
                })
            }

            $scope.loadOnCreate = function(item) {
            	  mainService.withdata('put','/user/service/createReport/1/'+item.planid,item)
                  .then(function(data){
                      if(data.report=='true'){
                         
                          if(data.minid==1){
                              $state.go('restricted.pages.weeklyData',{id:data.wrid});
                              sweet.show('', 'Тайлан амжилттай үүслээ', 'success');
                          }
                          else{
                              $state.go('restricted.pages.weeklyDataCoal',{id:data.wrid});
                              sweet.show('', 'Тайлан амжилттай үүслээ', 'success');
                          }
                      }
                      else{
                          sweet.show('Анхаар!', 'Тайлан үүссэн байна...', 'error');
                      }

                  })
             /*   sweet.show({
                    title:"Үндсэн ТЗ-ийн дугаар: "+($filter('filter')($scope.newWeekReportList, { id: item.planid }, true)[0]).licenseXB+"",
                    text: "Та "+item.weekid+"-р долоо хоногийн тайлан үүсгэх үү?",
                    type: 'info',
                    showCancelButton: true,
                    confirmButtonColor: '#DD6B55',
                    confirmButtonText: 'Тийм',
                    cancelButtonText: 'Үгүй',
                    closeOnConfirm: false,
                    closeOnCancel: false,
                    showLoaderOnConfirm: true
                }, function(inputvalue) {
                    if (inputvalue) {
                      
                    }else{
                        sweet.show('Анхаар!', 'Үйлдэл амжилтгүй...', 'error');
                    }

                });*/

            };

            $scope.loadOnView = function (item) {
                if (item.divisionid == 1) {
                    $state.go('restricted.pages.weeklyData', {id: item.id});
                }
                else {
                    $state.go('restricted.pages.weeklyDataCoal', {id: item.id});
                }
            };

            $scope.loadOnSend = function (i) {
                sweet.show({
                    title: 'Confirm',
                    text: 'Delete this file?',
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#DD6B55',
                    confirmButtonText: 'Yes, delete it!',
                    closeOnConfirm: false,
                    closeOnCancel: false,
                    showLoaderOnConfirm: true
                }, function (inputvalue) {
                    if (inputvalue) {
                        mainService.withdomain('put', '/logic/sendform/' + i)
                            .then(function () {
                                $(".conf .k-grid").data("kendoGrid").dataSource.read();
                                sweet.show('Deleted!', 'The file has been deleted.', 'success');
                            });
                    } else {
                        sweet.show('Cancelled', 'Your imaginary file is safe :)', 'error');
                    }

                });
            };


            $scope.gWeekStatus = {
                dataSource: {
                    transport: {
                        read: {
                            url: "/user/angular/WeeklyRegistrationStatus",
                            contentType: "application/json; charset=UTF-8",
                            type: "POST"
                        },
                        parameterMap: function (options) {
                            return JSON.stringify(options);
                        }
                    },
                    schema: {
                        data: "data",
                        total: "total",
                        model: {
                            id: "id"
                        }
                    },
                    pageSize: 10,
                    serverFiltering: true,
                    serverPaging: true,
                    serverSorting: true
                },
                filterable: true,
                toolbar: kendo.template($("#add").html()),
                sortable: {
                    mode: "multiple",
                    allowUnsort: true
                },
               /* scrollable: {
                    virtual: true
                },*/
                resizable: true,
                pageable: {
                    refresh: true,
                    buttonCount: 3
                },
                columns: [
                    {field: "licenseXB", title: "<span data-translate='License number'></span>"},
                    {field: "licensenum", title: "<span data-translate='License number'></span>"},
                    {field: "weekstr", title: "<span data-translate='Report year'></span>"},
                    {
                        template: kendo.template($("#status").html()),
                        field: "repstatusid",
                        title: "<span data-translate='Status'></span>"
                    },
                    {field: "submissiondate", title: "<span data-translate='Submitted date'></span>"},
                    {field: "approveddate", title: "<span data-translate='Received date'></span>"},
                    {
                        template: kendo.template($("#main").html()), width: "120px"
                    }],
                editable: "popup"
            };

        }
    ]);
