<script   setup>
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { searchServer} from "@/api/teacher.js"


const tableData = ref([
  {
    no: '20160503',
    name: '李东旭',
    phone: '18933218989'
  },
  {
    no: '20160503',
    name: '李东旭',
    phone: '18933218989'
  },
  {
    no: '20160503',
    name: '李东旭',
    phone: '18933218989'
  },
])


const currentPage = ref(1)
const pageSize = ref(3)
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(100)
const handleSizeChange = async (number) => {
  console.log(" items per page", number)
  searchBody.value.pageSize = number;
  await search()
}
const handleCurrentChange = async (number) => {
  console.log("current page: ", number)
  searchBody.value.pageNum = number
  await search()
}
// 搜索体
const searchBody = ref({
  name: "",
  no: "",
  pageNum: 1,
  pageSize: 3
})


//-----定义函数-----
const search = async () => {
  const result = await searchServer(searchBody);
  tableData.value = result.data.items;
  allNumber.value = result.data.total;
}

search();

const searchButton = () => {
  searchBody.value.pageNum = 1;
  searchBody.value.pageSize = 5;
  search()
}

const reset = () => {
  searchBody.value.searchName = "";
  searchBody.value.no='';
}

const teacherModel = ref({
  id: "",
  no: "",
  name: "",
  phone: ""
})

const emit=defineEmits(['event'])
const handleClick=(val)=>{
  emit('event',val)
}
</script>

<template>
  <div>
    <div>
      <el-input v-model="searchBody.no" style="width: 220px" placeholder="教师编号" :prefix-icon="Search" />
      <el-input v-model="searchBody.name" style="width: 220px;margin-left:10px" placeholder="教师姓名"
        :prefix-icon="Search" />
      <el-button type="primary" style="margin-left: 10px;" @click="searchButton">搜索</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div>
      <el-table :data="tableData" 
      highlight-current-row
      @current-change="handleClick" >
        <el-table-column prop="no" label="教师编号" />
        <el-table-column prop="name" label="教师姓名" />
        <el-table-column prop="phone" label="教师电话" />
      </el-table>
    </div>

    <div>
      <!--分页插件-->
      <el-pagination style="float: right;
      margin-top: 20px" v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[3, 5, 10]"
        :disabled="disabled" :background="background" layout="prev, pager, next, jumper,total, sizes" :total="allNumber"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>
    
  </div>
</template>

<style scoped></style>

