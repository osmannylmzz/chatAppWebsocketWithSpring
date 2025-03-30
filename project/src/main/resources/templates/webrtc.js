let localStream;
let peerConnection;
const configuration = {
    iceServers: [
        {
            urls: "stun:stun.l.google.com:19302"  // Google'ın STUN sunucusu
        }
    ]
};

// WebSocket bağlantısını başlatıyoruz
let socket = new WebSocket("ws://localhost:8080/ws"); // Backend'in WebSocket endpoint'i

socket.onopen = () => {
    console.log("WebSocket bağlantısı açıldı.");
};

socket.onmessage = (event) => {
    const message = JSON.parse(event.data);

    if (message.type === "offer") {
        // Teklif alındığında yanıt oluştur
        handleOffer(message.offer);
    } else if (message.type === "candidate") {
        // ICE adayı alındığında işle
        handleCandidate(message.candidate);
    } else if (message.type === "answer") {
        // Yanıt alındığında bağlantıyı kur
        handleAnswer(message.answer);
    }
};

// Arama başlatma fonksiyonu
document.getElementById("startCallBtn").onclick = startCall;

function startCall() {
    // Sesli arama başlat
    navigator.mediaDevices.getUserMedia({ audio: true, video: false })
        .then((stream) => {
            localStream = stream;
            document.getElementById("localAudio").srcObject = localStream;
            createPeerConnection();
            localStream.getTracks().forEach(track => {
                peerConnection.addTrack(track, localStream);
            });
            peerConnection.createOffer()
                .then((offer) => peerConnection.setLocalDescription(offer))
                .then(() => {
                    // WebSocket üzerinden sunucuya teklif gönderilecek
                    sendToServer({
                        type: "offer",
                        offer: peerConnection.localDescription
                    });
                })
                .catch((error) => {
                    console.error("Hata:", error);
                });
        })
        .catch((error) => {
            console.error("Mikrofon alırken hata:", error);
        });
}

function createPeerConnection() {
    peerConnection = new RTCPeerConnection(configuration);

    peerConnection.onicecandidate = (event) => {
        if (event.candidate) {
            sendToServer({
                type: "candidate",
                candidate: event.candidate
            });
        }
    };

    peerConnection.ontrack = (event) => {
        const remoteAudio = document.getElementById("remoteAudio");
        remoteAudio.srcObject = event.streams[0]; // Karşı tarafın sesini al
    };
}

function sendToServer(message) {
    // WebSocket üzerinden sunucuya mesaj gönder
    socket.send(JSON.stringify(message));
}

// Teklif (offer) mesajını işleme
function handleOffer(offer) {
    peerConnection.setRemoteDescription(new RTCSessionDescription(offer))
        .then(() => {
            return peerConnection.createAnswer();
        })
        .then((answer) => {
            return peerConnection.setLocalDescription(answer);
        })
        .then(() => {
            // Yanıtı sunucuya gönder
            sendToServer({
                type: "answer",
                answer: peerConnection.localDescription
            });
        })
        .catch((error) => {
            console.error("Teklif işlenirken hata:", error);
        });
}

// ICE adayı (candidate) mesajını işleme
function handleCandidate(candidate) {
    peerConnection.addIceCandidate(new RTCIceCandidate(candidate))
        .catch((error) => {
            console.error("ICE adayı eklenirken hata:", error);
        });
}

// Yanıt (answer) mesajını işleme
function handleAnswer(answer) {
    peerConnection.setRemoteDescription(new RTCSessionDescription(answer))
        .catch((error) => {
            console.error("Yanıt işlenirken hata:", error);
        });
}
