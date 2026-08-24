<script setup>
defineProps({
  requests: {
    type: Array,
    required: true
  }
})

const emit = defineEmits([
  'update-status',
  'remove'
])
</script>

<template>
  <div class="card">

    <h2>Internship Applications</h2>

    <table>

      <thead>
        <tr>
          <th>Company</th>
          <th>Position</th>
          <th>Status</th>
          <th>Priority</th>
          <th>Actions</th>
        </tr>
      </thead>

      <tbody>

        <tr
          v-for="application in requests"
          :key="application.id"
        >

          <td>
            <RouterLink
              :to="{
                name: 'application-details',
                params: { id: application.id }
              }"
            >
              {{ application.companyName }}
            </RouterLink>
          </td>

          <td>
            {{ application.positionTitle }}
          </td>

          <td>
            <select
              :value="application.status"
              @change="
                emit(
                  'update-status',
                  application.id,
                  $event.target.value
                )
              "
            >
              <option value="INTERESTED">Interested</option>
              <option value="APPLIED">Applied</option>
              <option value="SHORTLISTED">Shortlisted</option>
              <option value="INTERVIEW">Interview</option>
              <option value="OFFERED">Offered</option>
              <option value="ACCEPTED">Accepted</option>
              <option value="REJECTED">Rejected</option>
              <option value="WITHDRAWN">Withdrawn</option>
            </select>
          </td>

          <td>
            {{ application.priority }}
          </td>

          <td>
            <button
              @click="emit('remove', application.id)"
            >
              Delete
            </button>
          </td>

        </tr>

      </tbody>

    </table>

  </div>
</template>