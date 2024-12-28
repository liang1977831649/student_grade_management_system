import { createRouter, createWebHistory } from "vue-router";

import Login from "@/components/Login.vue";
import Home from "@/components/Home.vue";
import user from "@/store/useInfo.js";
import { useToken } from "@/store/token";
import dynamicRouter from "@/store/routerStore";

const routers = [
  {
    path: "/",
    component: Login,
    name: "login",
  },
  {
    path: "/home",
    component: Home,
    name: "home",
    // redirect: "/main",
    // children: [
    //   { path: "/main", component: () => import("@/components/view/Main.vue") },
    //   {
    //     path: "/courseInfo",
    //     component: () => import("@/components/view/CourseInfo.vue"),
    //   },
    //   {
    //     path: "/userInfo",
    //     component: () => import("@/components/view/UserInfo.vue"),
    //   },
    //   {
    //     path: "/teacher",
    //     component: () => import("@/components/view/Teacher.vue"),
    //   },
    //   {
    //     path: "/student",
    //     component: () => import("@/components/view/Student.vue"),
    //   },
    // {path:'/courseInfo',component:CourseInfo},
    // ],
  },
  {
    path: "/404",
    name: "404",
    component: () => import("../components/404.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes: routers,
});

//配置前置路由
router.beforeEach((to, from, next) => {
  //如果当前路径是登录路径的话，就需要清除所有的信息
  if (to.path === "/") {
    useToken().removeToken();
    dynamicRouter().removeRouter();
    user().removeUser();
  }

  //先获取token和路由列表
  let useDynamicRouter = dynamicRouter();
  //获取所有路由（包括子路由）数组
  const allRoutes = router.getRoutes();
  //判断当前访问路径是否在子路由中
  allRoutes.forEach((item) => {
    //如果在直接跳转
    if (item.path === to.path) {
      //放行
      next();
    }
  });
  //如果不在就从pinia里面获取路由信息，动态再添加配置路由表
  //在这里设置一个flag，如果再useDynamicRouter的路由表遍历的过程中，发现具有所访问的路由，那就设置为1，如果没有就设置为0
  let flag = 0;
  useDynamicRouter.router.forEach((item) => {
    if (item.path === to.path) {
      flag = 1;
    }
    router.addRoute("home", {
      path: item.path,
      component: () =>
        import("../components/view/" + item.componentName + ".vue"),
    });
  });
  //如果flag还是0,那么就访问404
  if (flag == 0) {
    next("/404");
  }
  //再一次重新访问，相当于第二次访问前置路由器，第二次访问时，因为已经配置了路由，因此if(item.path===to.path)为真，进入next();
  next(to.path);
});

export default router;
