<script   setup>
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { searchServer, addServer, updateServer, deleteServer } from "@/api/course.js"
import Teacher_choose from "@/components/view/Teacher_choose.vue";
import { ElMessage,ElMessageBox } from 'element-plus';
const tableData = ref([
  {
    no: '20160503',
    name: 'english',
    times: '189',
    teacher: 'tom',
    des: 'gaokao'
  },
  {
    no: '20160503',
    name: 'english',
    times: '189',
    teacher: 'tom',
    des: 'gaokao'
  },
  {
    no: '20160503',
    name: 'english',
    times: '189',
    teacher: 'tom',
    des: 'gaokao'
  }
])


// import type { ComponentSize } from 'element-plus'
// const size = ref<ComponentSize>('default')
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
  searchName: "",
  teacherName: "",
  courseNo: "",
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
  searchBody.value.pageSize = 3;
  search()
}
const reset = () => {
  searchBody.value.searchName = "";
  searchBody.value.teacherName = "";
  searchBody.value.courseNo = "";
}

const deleteInfo = async (row) => {
  ElMessageBox.confirm(
    '你确认要删除该教师信息吗?',
    '温馨提示',
    {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: "warning",
    })
    .then(async () => {
      await deleteServer(row.id)
      await search();
      ElMessage.success('删除成功')
    })
    .catch(() => {
      ElMessage.info('用户取消了删除')
    })
}

const courseModel = ref({
  id: '',
  no: '',
  name: '',
  teacherId: '',
  teacherName: '',
  times: '',
  des: ''
})

const cleanCourseModel = () => {
  courseModel.value.id = '';
  courseModel.value.no = '';
  courseModel.value.name = '';
  courseModel.value.teacherId = '';
  courseModel.value.teacherName = '';
  courseModel.value.times = '';
  courseModel.value.des = '';
}

const rules = {
  no: [
    { required: true, message: "编号不为空", trigger: 'blur' },
    { min: 4, message: '编号最少4个字符', trigger: 'blur' }
  ],
  name: [
    { required: true, message: "课程名称不为空", trigger: 'blur' },
    { min: 2, message: '课程名称最少2个字符', trigger: 'blur' }
  ],
  teacherName: [
    { required: true, message: "请指定教师", trigger: 'blur' }
  ],
  times: [
    { required: true, message: "课时不为空", trigger: 'blur' }
  ]
}
const form = ref(null)
const AddAndEdit = () => {
  form.value.validate((async valid => {
    if (valid) {
      if (title.value === '添加') {
        console.log('添加', courseModel.value);
        await addServer(courseModel);
        ElMessage.success('添加成功')
      } else {
        // console.log('编辑', courseModel.value);
        await updateServer(courseModel);
        ElMessage.success('修改成功')
      }
      // cleanCourseModel();
      await search();
      dialogVisible.value = false;
    }
  }))
}

const dialogVisible = ref(false);
const title = ref('添加');

const addButton = () => {
  title.value = '添加'
  dialogVisible.value = true;
}
const editButton = (row) => {
  courseModel.value.id = row.id;
  courseModel.value.no = row.no;
  courseModel.value.name = row.name;
  courseModel.value.times = row.times;
  courseModel.value.teacherId = row.teacherId;
  courseModel.value.teacherName = row.teacherName;
  courseModel.value.des = row.des;
  title.value = '编辑'
  dialogVisible.value = true;
}

const innerDialog = ref(false);


const reception = (val) => {
  console.log("val=", val);
  if (val !== null) {
    courseModel.value.teacherId = val.id;
    courseModel.value.teacherName = val.name;
  }
  console.log(courseModel.value.teacherName);
  console.log(courseModel.value.teacherId);
}
const innerDialogCancel = () => {
  innerDialog.value = false;
}
const AddAndEditCancel = () => {
  dialogVisible.value = false;
  form.value.resetFields();//清除或重置校验结果
  cleanCourseModel();
}

</script>

<template>
  <div>
    <div>
      <el-input v-model="searchBody.searchName" style="width: 220px" placeholder="课程名" :prefix-icon="Search" />
      <el-input v-model="searchBody.courseNo" style="width: 220px;margin-left:10px" placeholder="课程编号"
        :prefix-icon="Search" />
      <el-input v-model="searchBody.teacherName" style="width: 220px;margin-left:10px" placeholder="任课老师"
        :prefix-icon="Search" />
      <el-button type="primary" style="margin-left: 10px;" @click="searchButton">搜索</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div>
      <el-button type="primary" style="margin: 10px 0 10px 0;" @click="addButton()">新增</el-button>
    </div>
    <div>
      <el-table :data="tableData" stripe style="width: 100%">
        <el-table-column prop="no" label="课程编号" />
        <el-table-column prop="name" label="课程名称" />
        <el-table-column prop="times" label="课时" />
        <el-table-column prop="teacherName" label="任课老师" />
        <el-table-column prop="des" label="课程描述" />
        <el-table-column fixed="right" label="操作" min-width="120">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="editButton(row)">
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="deleteInfo(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <!--分页插件-->
      <el-pagination style="float: right;
      margin-top: 20px" v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[3, 5, 10]"
        :disabled="disabled" :background="background" layout="prev, pager, next, jumper,total, sizes" :total="allNumber"
        @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </div>

    <!-- 添加或者编辑弹窗 -->
    <div>
      <el-dialog v-model="dialogVisible" :title="title" width="350px">
        <el-form :model="courseModel" :rules="rules" ref="form">
          <el-form-item label="课程编号" prop="no">
            <el-input v-model="courseModel.no" />
          </el-form-item>
          <el-form-item label="课程名称" prop="name">
            <el-input v-model="courseModel.name" />
          </el-form-item>
          <el-form-item label="课程时长" prop="times">
            <el-input type="number" v-model="courseModel.times" />
          </el-form-item>

          <el-form-item label="授课老师" prop="teacherName">
            <el-input v-model="courseModel.teacherName" @click="innerDialog = true" />
          </el-form-item>

          <!-- 添加教师弹窗 -->
          <el-dialog v-model="innerDialog" width="1000" title="请选择授课教师" append-to-body>
            <!-- 教师页面选择 -->
            <Teacher_choose @event="reception"></Teacher_choose>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="innerDialogCancel()">取消</el-button>
                <el-button type="primary" @click="innerDialog = false">确认</el-button>
              </span>
            </template>
          </el-dialog>
          <!-- 添加教师弹窗结束 -->

          <el-form-item label="课程描述">
            <el-input v-model="courseModel.des" />
          </el-form-item>
        </el-form>

        <template #footer>
          <div class="dialog-footer">
            <el-button @click="AddAndEditCancel()">取消</el-button>
            <el-button type="primary" @click="AddAndEdit()">
              确定
            </el-button>
          </div>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<style scoped></style>

