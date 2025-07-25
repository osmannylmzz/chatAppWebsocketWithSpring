Bu proje, Spring Boot ile ve temel HTML/CSS/JS kullanılarak geliştirilen ve gerçek zamanlı birebir/grup mesajlaşmayı destekleyen bir iletişim sistemidir. Kullanıcılar WebSocket üzerinden mesajlaşabilir, MongoDB’de mesaj geçmişi saklanır ve JWT ile güvenli oturum yönetimi sağlanır.
Ayrıca kullanıcılar WebRTC altyapısı ile sesli arama yapabilir. Bu özellik sinyal alışverişi için WebSocket kullanır, medya aktarımı doğrudan tarayıcılar arasında gerçekleşir.
Proje, AWS EC2 üzerinde canlı olarak yayınlanmıştır.

🚀 Temel Özellikler
✅ Kullanıcı kayıt & giriş (JWT ile)
✅ Birebir mesajlaşma (gerçek zamanlı)
✅ Grup mesajlaşma
✅ Mesaj durumu takibi (gönderildi / okundu)
✅ WebRTC ile sesli arama
✅ MongoDB ile mesaj geçmişi
✅ AWS EC2 üzerinde yayınlama
✅ Mysql ile kullanıcı bilgileri tutma

🔊 Sesli Arama Özelliği (WebRTC)
Kullanıcılar birebir görüşmelerde anlık sesli arama başlatabilir.

Arama başlatma, cevaplama, reddetme gibi durumlar WebSocket mesajları ile yönetilir.

WebRTC offer/answer/candidate yapısı kullanılmıştır.

ICE sunucuları (STUN/TURN) tanımlanmıştır.

Görüşme bilgileri MongoDB’de aşağıdaki bilgilerle saklanır:

Başlatan & Alıcı kullanıcı adı

Arama durumu (başlatıldı, cevaplandı, reddedildi, sonlandırıldı)

Başlangıç & Bitiş zamanı

Süre & sessize alma, beklemeye alma durumu



Ekstra: Sesli görüşme backend’i ile WebRTC signaling yönetimi Spring Boot tarafında gerçekleştirilmiştir.

🛠️ Kullanılan Teknolojiler
Katman	Teknoloji
Backend	Spring Boot, WebSocket (STOMP), MongoDB
Authentication	Spring Security + JWT
Real-Time Messaging	WebSocket + STOMP
Sesli Arama	WebRTC + ICE/STUN/TURN
Deployment	AWS EC2 (Ubuntu 22.04)
Mysql 

Uygulama içi detaylı görüntüleri screenshots klasöründe paylaştım.

