<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRequestStore } from '../stores/requests'
import RequestForm from '../components/RequestForm.vue'
import RequestTable from '../components/RequestTable.vue'

const store = useRequestStore()

const searchText = ref('')
const selectedStatus = ref('ALL')
const sortBy = ref('Company Name')

onMounted(() => {
  store.fetchRequests()
})

const filteredApplications = computed(() => {

  let results = store.requests.filter(application => {

    const search =
      searchText.value.toLowerCase().trim()

    const matchesSearch =
      application.companyName
        .toLowerCase()
        .includes(search)
      ||
      application.positionTitle
        .toLowerCase()
        .includes(search)

    const matchesStatus =
      selectedStatus.value === 'ALL'
      ||
      application.status === selectedStatus.value

    return matchesSearch && matchesStatus
  })

  if (sortBy.value === 'Company Name') {
    results.sort((a, b) =>
      a.companyName.localeCompare(b.companyName)
    )
  }

  if (sortBy.value === 'Closing Date') {
    results.sort((a, b) =>
      new Date(a.closingDate) - new Date(b.closingDate)
    )
  }

  if (sortBy.value === 'Priority') {

    const priorityOrder = {
      HIGH: 1,
      MEDIUM: 2,
      LOW: 3
    }

    results.sort((a, b) =>
      priorityOrder[a.priority] -
      priorityOrder[b.priority]
    )
  }

  return results
})
</script>

<template>
  <section>

    <h2>Manage Internship Applications</h2>

    <p v-if="store.loading">
      Loading applications...
    </p>

    <p v-if="store.error" class="error">
      {{ store.error }}
    </p>

    <div class="card">

      <h3>Search and Filter</h3>

      <input
        v-model="searchText"
        placeholder="Search company or position"
      >

      <select v-model="selectedStatus">
        <option value="ALL">All Statuses</option>
        <option value="INTERESTED">Interested</option>
        <option value="APPLIED">Applied</option>
        <option value="SHORTLISTED">Shortlisted</option>
        <option value="INTERVIEW">Interview</option>
        <option value="OFFERED">Offered</option>
        <option value="ACCEPTED">Accepted</option>
        <option value="REJECTED">Rejected</option>
        <option value="WITHDRAWN">Withdrawn</option>
      </select>

      <select v-model="sortBy">
        <option>Company Name</option>
        <option>Closing Date</option>
        <option>Priority</option>
      </select>

    </div>

    <RequestForm
      @submit="store.addRequest"
    />

    <p
  v-if="
    !store.loading &&
    filteredApplications.length === 0
  "
>
  No internship applications found.
</p>

<RequestTable
  v-else
  :requests="filteredApplications"
  @update-status="
    (id, status) =>
      store.updateStatus(id, status)
  "
  @remove="store.removeRequest"
/>

  </section>
</template>