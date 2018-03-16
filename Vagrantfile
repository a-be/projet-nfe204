$script_start_redis_master_slave = <<SCRIPT
docker network create redis-master-slave
sudo docker run -p 6379:6379 --net=redis-master-slave --name redis-master -d redis
sudo docker run -p 6479:6379 --net=redis-master-slave --name redis-slave-2 -d redis
sudo docker run -p 6579:6379 --net=redis-master-slave --name redis-slave-3 -d redis
su vagrant << EOF
source /etc/profile.d/rvm.sh
rvm use "ruby-2.2.2"
redis-stat --server=8181 --daemon 172.18.0.2:6379 172.18.0.3:6379 172.18.0.4:6379
EOF
SCRIPT

$script_start_redis_cluster = <<SCRIPT
docker network create redis-cluster
sudo docker run -p 7001:7001 -v /vagrant/7001/redis.conf:/usr/local/etc/redis/redis.conf --net=redis-cluster --name redis-1 -d redis redis-server /usr/local/etc/redis/redis.conf
sudo docker run -p 7002:7002 -v /vagrant/7002/redis.conf:/usr/local/etc/redis/redis.conf --net=redis-cluster --name redis-2 -d redis redis-server /usr/local/etc/redis/redis.conf
sudo docker run -p 7003:7003 -v /vagrant/7003/redis.conf:/usr/local/etc/redis/redis.conf --net=redis-cluster --name redis-3 -d redis redis-server /usr/local/etc/redis/redis.conf
sudo docker run -p 7004:7004 -v /vagrant/7004/redis.conf:/usr/local/etc/redis/redis.conf --net=redis-cluster --name redis-4 -d redis redis-server /usr/local/etc/redis/redis.conf
sudo docker run -p 7005:7005 -v /vagrant/7005/redis.conf:/usr/local/etc/redis/redis.conf --net=redis-cluster --name redis-5 -d redis redis-server /usr/local/etc/redis/redis.conf
sudo docker run -p 7006:7006 -v /vagrant/7006/redis.conf:/usr/local/etc/redis/redis.conf --net=redis-cluster --name redis-6 -d redis redis-server /usr/local/etc/redis/redis.conf
su vagrant << EOF
source /etc/profile.d/rvm.sh
rvm use "ruby-2.2.2"
sudo gem install redis
wget http://download.redis.io/releases/redis-4.0.8.tar.gz
tar -xvf redis-4.0.8.tar.gz
echo "yes" | /home/vagrant/redis-4.0.8/src/redis-trib.rb create --replicas 1 172.18.0.2:7001 172.18.0.3:7002 172.18.0.4:7003 172.18.0.5:7004 172.18.0.6:7005 172.18.0.7:7006
redis-stat --server=8181 --daemon 172.18.0.2:7001 172.18.0.3:7002 172.18.0.4:7003 172.18.0.5:7004 172.18.0.6:7005 172.18.0.7:7006
EOF
SCRIPT

$script_install_redis_stat = <<SCRIPT
su vagrant << EOF
source /etc/profile.d/rvm.sh
rvm use "ruby-2.2.2"
sudo gem install redis-stat
EOF
SCRIPT

$script_install_ruby = <<SCRIPT
su vagrant << EOF
source /etc/profile.d/rvm.sh
sudo rvm install "ruby-2.2.2"
EOF
SCRIPT

$script_install_rvm = <<SCRIPT
sudo gpg --keyserver hkp://keys.gnupg.net --recv-keys 409B6B1796C275462A1703113804BB82D39DC0E3 7D2BAF1CF37B13E2069D6956105BD0E739499BDB
sudo apt-get update
sudo apt-get install -y software-properties-common
sudo apt-add-repository -y ppa:rael-gc/rvm
sudo apt-get update
sudo apt-get install -y rvm
SCRIPT

$script_install_java = <<SCRIPT
sudo apt-add-repository -y ppa:webupd8team/java
sudo apt update
sudo echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
sudo apt install -y oracle-java8-installer
SCRIPT

$script_run_application = <<SCRIPT
/usr/lib/jvm/java-8-oracle/bin/java -Dserver.port=8686 -Dpage-size=100 -jar /vagrant/nfe-204-0.0.1-SNAPSHOT.jar &
/usr/lib/jvm/java-8-oracle/bin/java -Dserver.port=8787 -Dpage-size=100 -jar /vagrant/nfe-204-0.0.1-SNAPSHOT.jar &
/usr/lib/jvm/java-8-oracle/bin/java -Dserver.port=8585 -Dpage-size=100 -jar /vagrant/nfe-204-0.0.1-SNAPSHOT.jar &
SCRIPT

Vagrant.configure("2") do |config|
  config.vm.box = "williamyeh/ubuntu-trusty64-docker"
  config.vm.network "public_network", ip: "192.168.0.17"
  config.vm.network "forwarded_port", guest: 22, host: 2222
  for i in [8181,6379,6479,6579]
    config.vm.network "forwarded_port", guest: i, host: i
  end
  for i in 7001..7006
    config.vm.network "forwarded_port", guest: i, host: i
  end
  config.vm.provision "shell", inline: $script_install_rvm
  config.vm.provision "shell", inline: $script_install_ruby
  config.vm.provision "shell", inline: $script_install_redis_stat
  #config.vm.provision "shell", inline: $script_start_redis_cluster
  config.vm.provision "shell", inline: $script_start_redis_master_slave
  config.vm.provision "shell", inline: $script_install_java
end