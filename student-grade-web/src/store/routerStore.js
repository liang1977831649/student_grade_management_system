import { ref } from "vue";
import { defineStore } from "pinia";

const dynamicRouter = defineStore(
  "router",
  () => {
    let router = ref([]);

    const setRouter = (newRouter) => {
      router.value = newRouter.slice();
    };

    const removeRouter = () => {
      router.value = [];
    };

    const pushRouter = (childRouter) => {
      //有时候我们页面刷新后，原本的路由会保存，但是因为调用这个函数的函数，会再一次将路由push进来
      //因此需要判断，原本的子路由是否存在pinia中
      //如果存在，就直接跳过，如果不存在，就加入进来
      let flag = 0;
      for (let i = 0; i < router.value.length; i++) {
        let item = router.value[i];
        if (item.path === childRouter.path) {
          flag = 1;
          break;
        }
      }
      if (flag == 0) {
        router.value.push(childRouter);
      }
    };

    return { router, setRouter, removeRouter, pushRouter };
  },
  { persist: true }
);

export default dynamicRouter;
