package uk.co.victoriajanedavis.chatapp.data.realtime.fcm

import io.reactivex.Flowable
import uk.co.victoriajanedavis.chatapp.data.model.websocket.*
import uk.co.victoriajanedavis.chatapp.data.realtime.RealtimeEventResolver
import javax.inject.Inject

class FirebaseMessagingStreams @Inject constructor(
    private val messageDataStream: FirebaseMessageDataStream,
    private val eventResolver: RealtimeEventResolver
) {

    fun chatMessageStream(): Flowable<MessageWsModel> {
        return allEventsStream().ofType(MessageWsModel::class.java)
    }

    fun acceptedFriendRequestStream(): Flowable<AcceptedFriendRequestWsModel> {
        return allEventsStream().ofType(AcceptedFriendRequestWsModel::class.java)
    }

    fun canceledFriendRequestStream(): Flowable<CanceledFriendRequestWsModel> {
        return allEventsStream().ofType(CanceledFriendRequestWsModel::class.java)
    }

    fun createdFriendRequestStream(): Flowable<CreatedFriendRequestWsModel> {
        return allEventsStream().ofType(CreatedFriendRequestWsModel::class.java)
    }

    fun rejectedFriendRequestStream(): Flowable<RejectedFriendRequestWsModel> {
        return allEventsStream().ofType(RejectedFriendRequestWsModel::class.java)
    }

    private fun allEventsStream(): Flowable<RealtimeModel> {
        return messageDataStream.getStream()
            .map(eventResolver::resolveEventFromMap)
    }
}