<script setup>
import {get, post} from "@/net/index.js";

let tableData = [
  /*{
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },*/
]

const getTableData = () => {
  get('/user/getUserList', (data) => {
    tableData = data;
    console.log(tableData)
  })
}

const createUer = () => {

}

onMounted(() => {
  getTableData();
})

</script>

<template>
  <div>
    <div class="query-container">
      <el-form :inline="true" :label-position="'right'"  class="demo-form-inline">
        <el-form-item label="账号:">
          <el-input placeholder="账号" clearable />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input  placeholder="姓名" clearable />
        </el-form-item>
        <el-form-item label="注册时间:">
          <el-date-picker
              type="date"
              placeholder="选择日期"
              clearable
          />
        </el-form-item>
        <el-form-item>
          <el-button plain>重置</el-button>
          <el-button type="primary">查询</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="list-container">
      <div class="operate-buttons">
        <el-button type="primary" @click="createUer()">创建用户</el-button>
      </div>
      <el-table
          :data="tableData"
          :default-sort="{ prop: 'registerAt', order: 'descending' }"
          header-row-class-name="list-header"
      >
        <el-table-column prop="username" label="账号" />
        <el-table-column prop="realName" label="姓名" />
        <el-table-column prop="enabled" label="可用状态" />
        <el-table-column prop="registerAt" label="注册时间" sortable />
      </el-table>
    </div>
  </div>
</template>

<style scoped>
.query-container {
  margin-top: 20px;
  margin-left: 20px;
  margin-right: 20px;
  padding-top: 20px;
  padding-right: 20px;
  padding-left: 20px;
  background-color: rgba(255, 255, 255, 1);
  border-radius: 6px;
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.03), 0 1px 6px -1px rgba(0, 0, 0, 0.02), 0 2px 4px 0 rgba(0, 0, 0, 0.02);
}
.list-container {
  margin-top: 10px;
  margin-left: 20px;
  margin-right: 20px;
  padding: 0 10px 10px 10px;
  border-radius: 6px;
  background-color: rgba(255, 255, 255, 1);
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.03), 0 1px 6px -1px rgba(0, 0, 0, 0.02), 0 2px 4px 0 rgba(0, 0, 0, 0.02);
  .operate-buttons {
    padding-top: 10px;
    padding-bottom: 10px;
  }
  /deep/ .list-header th {
    background-color: rgba(0, 0, 0, 0.08);
  }
}

</style>

<style>
.demo-form-inline .el-input {
  --el-input-width: 180px;
}
</style>
