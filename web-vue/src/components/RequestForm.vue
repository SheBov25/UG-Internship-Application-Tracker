<script setup>
import { reactive } from 'vue'

const emit = defineEmits(['submit'])

const form = reactive({
  companyName: '',
  positionTitle: '',
  category: 'INFORMATION_TECHNOLOGY',
  applicationDate: '',
  closingDate: '',
  status: 'INTERESTED',
  contactPerson: '',
  contactEmail: '',
  notes: ''
})

function submit() {

  if (!form.companyName.trim() ||
      !form.positionTitle.trim()) {
    return
  }

  emit('submit', { ...form })

  form.companyName = ''
  form.positionTitle = ''
  form.category = 'INFORMATION_TECHNOLOGY'
  form.applicationDate = ''
  form.closingDate = ''
  form.status = 'INTERESTED'
  form.contactPerson = ''
  form.contactEmail = ''
  form.notes = ''
}
</script>

<template>
  <form class="card" @submit.prevent="submit">

    <h2>Add Internship Application</h2>

    <label>Company Name</label>
    <input
      v-model="form.companyName"
      placeholder="e.g. GTT"
      required
    >

    <label>Position Title</label>
    <input
      v-model="form.positionTitle"
      placeholder="e.g. Software Development Intern"
      required
    >

    <label>Category</label>
    <select v-model="form.category">
      <option value="INFORMATION_TECHNOLOGY">
        Information Technology
      </option>
      <option value="BUSINESS">
        Business
      </option>
      <option value="ENGINEERING">
        Engineering
      </option>
      <option value="EDUCATION">
        Education
      </option>
      <option value="OTHER">
        Other
      </option>
    </select>

    <label>Application Date</label>
    <input
      v-model="form.applicationDate"
      type="date"
    >

    <label>Closing Date</label>
    <input
      v-model="form.closingDate"
      type="date"
    >

    <label>Status</label>
    <select v-model="form.status">
      <option value="INTERESTED">Interested</option>
      <option value="APPLIED">Applied</option>
      <option value="SHORTLISTED">Shortlisted</option>
      <option value="INTERVIEW">Interview</option>
      <option value="OFFERED">Offered</option>
      <option value="ACCEPTED">Accepted</option>
      <option value="REJECTED">Rejected</option>
      <option value="WITHDRAWN">Withdrawn</option>
    </select>

    <label>Contact Person</label>
    <input
      v-model="form.contactPerson"
      placeholder="Optional"
    >

    <label>Contact Email</label>
    <input
      v-model="form.contactEmail"
      type="email"
      placeholder="Optional"
    >

    <label>Notes</label>
    <textarea
      v-model="form.notes"
      placeholder="Additional information"
    ></textarea>

    <button type="submit">
      Add Application
    </button>

  </form>
</template>