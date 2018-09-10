layui.define(['admin', 'layer', 'element'], function (exports) {
    var admin = layui.admin;
    var layer = layui.layer;
    var element = layui.element;

    var index = {
        pageTabs: true,  // 是否开启多标签
        // 路由注册
        initRouter: function () {
            // 自动扫描side菜单注册
            $('.layui-layout-admin .layui-side .layui-nav a[lay-href]').each(function () {
                var menuName = $(this).text();
                var menuPath = $(this).attr('lay-href');
                if ('javascript:;' != menuPath && '' != menuPath) {
                    var key = menuPath.replace(/[?:=&/]/g, '_');
                    $(this).attr('href', '#!' + key);
                    Q.reg(key, function () {
                        index.loadView({
                            menuId: key,
                            menuPath: menuPath,
                            menuName: menuName
                        });
                    });
                } else {
                    $(this).attr('href', 'javascript:;');
                }
            });
            // 主页
            Q.init({
                index: 'home_console'
            });
            // tab选项卡切换监听
            element.on('tab(admin-pagetabs)', function (data) {
                var layId = $(this).attr('lay-id');
                Q.go(layId);
            });
        },
        // 加载主体部分
        loadView: function (param) {
            var menuId = param.menuId;
            var menuPath = param.menuPath;
            var menuName = param.menuName;
            var flag;  // 选项卡是否已添加
            var contentBody = '.layui-layout-admin .layui-body';
            // 判断是否开启了选项卡功能
            if (index.pageTabs) {
                $('.layui-layout-admin .layui-body .layui-tab .layui-tab-title>li').each(function () {
                    if ($(this).attr('lay-id') === menuId) {
                        flag = true;
                        return false;
                    }
                });
                if (!flag) {
                    element.tabAdd('admin-pagetabs', {
                        title: menuName,
                        id: menuId,
                        content: '<div id="' + menuId + '"></div>'
                    });
                }
                contentBody = '#' + menuId;
                element.tabChange('admin-pagetabs', menuId);
                admin.rollPage('auto');
                // 切换tab关闭表格内浮窗
                $('.layui-table-tips-c').trigger('click');
                admin.removeLoading('.layui-layout-admin .layui-body');
                // 解决切换tab滚动条时而消失的问题
                var $iframe = $('.layui-layout-admin .layui-body .layui-tab-content .layui-tab-item.layui-show .admin-iframe')[0];
                if ($iframe) {
                    $iframe.style.height = "99%";
                    $iframe.scrollWidth;
                    $iframe.style.height = "100%";
                }
            } else {
                $('.layui-body.admin-iframe-body').removeClass('admin-iframe-body');
            }
            if (!flag || admin.isRefresh) {
                admin.showLoading('.layui-layout-admin .layui-body');
                admin.ajax({
                    url: menuPath,
                    type: 'GET',
                    dataType: 'html',
                    success: function (result, status, xhr) {
                        $(contentBody).html(result);
                        admin.isRefresh = false;
                        element.render('breadcrumb');
                        admin.removeLoading('.layui-layout-admin .layui-body');
                    }
                });
            }
            admin.activeNav(Q.lash);
            // 移动设备切换页面隐藏侧导航
            if (document.body.clientWidth <= 750) {
                admin.flexible(true);
            }
        },
        // 检查多标签功能是否开启
        checkPageTabs: function () {
            if (index.pageTabs) {
                $('.layui-layout-admin').addClass('open-tab');
                // 如果开启多标签先加载主页
                element.tabAdd('admin-pagetabs', {
                    id: 'home_console',
                    title: '<i class="layui-icon layui-icon-home"></i>',
                    content: '<div id="home_console"></div>'
                });
                $('#home_console').load('home/console');
            } else {
                $('.layui-layout-admin').removeClass('open-tab');
            }
        },
        // 打开新页面
        openNewTab: function (param) {
            var menuId = param.menuId;
            var url = param.url;
            var title = param.title;

            Q.reg(menuId, function () {
                index.loadView({
                    menuId: menuId,
                    menuPath: url,
                    menuName: title
                });
            });

            Q.go(menuId);
        },
        // 关闭选项卡
        closeTab: function (menuId) {
            element.tabDelete('admin-pagetabs', menuId);
        },
        // 绑定事件监听
        bindEvent: function () {
            // 退出登录点击事件
            $('#btnLogout').click(function () {
                layer.confirm('确定退出登录？', function () {
                    location.replace('logout');
                });
            });

            // 修改密码点击事件
            $('#setPsw').click(function () {
                admin.popupRight('home/password');
            });

            // 个人信息点击事件
            $('#setInfo').click(function () {

            });

            // 消息点击事件
            $('#btnMessage').click(function () {
                admin.popupRight('home/message');
            });
        }
    };

    exports('index', index);
});
