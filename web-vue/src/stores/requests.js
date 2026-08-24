import { defineStore } from 'pinia'
import api from '../services/api'

export const useRequestStore = defineStore('requests', {

  state: () => ({
    requests: [],
    loading: false,
    error: ''
  }),

  getters: {

    appliedCount: (state) =>
      state.requests.filter(
        application => application.status === 'APPLIED'
      ).length,

    interviewCount: (state) =>
      state.requests.filter(
        application => application.status === 'INTERVIEW'
      ).length,

    offerCount: (state) =>
      state.requests.filter(
        application =>
          application.status === 'OFFERED' ||
          application.status === 'ACCEPTED'
      ).length,

    highPriorityCount: (state) =>
      state.requests.filter(
        application => application.priority === 'HIGH'
      ).length
  },

  actions: {

    async fetchRequests() {

      this.loading = true
      this.error = ''

      try {
        const response = await api.get('/requests')
        this.requests = response.data
      } catch (error) {
        this.error = error.message
      } finally {
        this.loading = false
      }
    },

    async addRequest(payload) {

      const response =
        await api.post('/requests', payload)

      this.requests.push(response.data)
    },

    async updateStatus(id, status) {

      const response =
        await api.patch(
          `/requests/${id}`,
          { status }
        )

      const index =
        this.requests.findIndex(
          application => application.id === id
        )

      if (index >= 0) {
        this.requests[index] = response.data
      }
    },

    async removeRequest(id) {

      await api.delete(
        `/requests/${id}`
      )

      this.requests =
        this.requests.filter(
          application => application.id !== id
        )
    }
  }
})